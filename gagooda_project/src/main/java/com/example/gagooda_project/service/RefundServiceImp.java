package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RefundServiceImp implements RefundService{
    RefundMapper refundMapper;
    AddressMapper addressMapper;
    CommonCodeMapper commonCodeMapper;
    OrderDetailMapper orderDetailMapper;
    OrderMapper orderMapper;
    ImageMapper imageMapper;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private static int imgCodeNum = 1000;

    public RefundServiceImp(RefundMapper refundMapper, AddressMapper addressMapper, CommonCodeMapper commonCodeMapper, OrderDetailMapper orderDetailMapper, OrderMapper orderMapper, ImageMapper imageMapper) {
        this.refundMapper = refundMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.orderMapper = orderMapper;
        this.imageMapper = imageMapper;
    }

    @Transactional
    public int registerOne(RefundDto refund, List<MultipartFile> imgFileList,
                           String imgPath,
                           String code,
                           int seq) throws Exception {
        int register = 0;
        imgCodeNum += 1;
        code = code + imgCodeNum;
        List<RefundDto> checkRefundList = null;
        // 주문 상세 단건 등록의 경우
        checkRefundList = refundMapper.findByOrderDetailId(refund.getOrderDetailId());
        OrderDto order = orderMapper.findById(refund.getOrderId());
        if (checkRefundList != null){ // db 조회가 되지만, 상태가 rf1(요청 취소)인 경우
            for (RefundDto checkRefund : checkRefundList){
                if(checkRefund.getRfDet().equals("rf1")){
                    continue;
                }else{ // 상태가 rf1(요청 취소) 이외의 코드인 경우
                    throw new Error();
                }
            }
            for(int i=0; i < imgFileList.size(); i++ ) {
                MultipartFile imgFile = imgFileList.get(i);
                if(imgFile!=null && !imgFile.isEmpty()) {
                    register = imageRegister(imgFile, imgPath, code, i+1);
                    if (register <= 0){

                    }
                }
            }
            refund.setImgCode(code);
            register += refundMapper.insertOne(refund);
        }else{ // db 정보가 없고 처음 등록하는 경우
            for(int i=0; i < imgFileList.size(); i++ ) {
                MultipartFile imgFile = imgFileList.get(i);
                register += imageRegister(imgFile, imgPath, code, i+1);
                refund.setImgCode(code);
                register += refundMapper.insertOne(refund);
            }
        }
        return register;
    }

    public List<RefundDto> showUserRefundList(int userId, int period, String startDate, String endDate, String detCode, PagingDto paging){
        if (startDate != null && startDate.equals(endDate)){
            startDate = startDate + "%";
            endDate = startDate;
        }
        int totalRows = refundMapper.countByUserIdAndDate(userId, period, startDate, endDate, detCode);
        paging.setTotalRows(totalRows);
        paging.setOrderField("reg_date");
        paging.setRows(5);
        return refundMapper.pageByUserIdAndDate(userId, period, startDate, endDate, detCode, paging);
    }
    public int showCountByUser(int userId, int period, String startDate, String endDate, String detCode){
        if (startDate != null && startDate.equals(endDate)){
            startDate = startDate + "%";
            endDate = startDate;
        }
        return refundMapper.countByUserIdAndDate(userId, period, startDate, endDate, detCode);
    }

    public List<RefundDto> showRefundList(Map<String, Object> searchFilter){
        if(!searchFilter.get("rfDet").equals("")){
            String rfDet = searchFilter.get("rfDet").toString();
            List<String> rfList = new ArrayList<>(Arrays.asList(rfDet.split(",")));
            String rfDetF = "'"+String.join("','", rfList)+"'";
            searchFilter.put("rfDet", rfDetF);
        }
        if(searchFilter.get("searchDiv").equals("all")){
            String allCol = "refund_id OR user_id OR uname OR email OR phone OR order_detail_id OR order_id " +
                    "OR cancel_amount OR reason OR post_code OR address OR address_detail OR receiver_name OR receiver_phone";
            searchFilter.put("searchDiv", allCol);
        }
        if(!searchFilter.get("searchWord").equals("")){
            String keyword = "%"+searchFilter.get("searchWord")+"%";
            searchFilter.put("searchWord", keyword);
        }
        PagingDto pagingDto = (PagingDto) searchFilter.get("paging");
        int totalRows = refundMapper.countPageAll(searchFilter);
        pagingDto.setRows(10);
        pagingDto.setTotalRows(totalRows);
        if (pagingDto.getOrderField() == null){
            pagingDto.setOrderField("reg_date");
        }
        searchFilter.put("paging",pagingDto);
        return refundMapper.pageAll(searchFilter);
    }

    public int countPageAll(Map<String,Object> searchFilter){ return refundMapper.countPageAll(searchFilter); }

    public RefundDto selectOne(int id){ return refundMapper.findById(id); }

    public int modifyOne(RefundDto refund, String auth){ return refundMapper.updateOne(refund, auth); }

    public List<AddressDto> showAddressListByUserId(int userId){
        return addressMapper.listByUserId(userId);
    }

    public AddressDto selectAddress(int addressId){
        return addressMapper.findById(addressId);
    }

    public List<CommonCodeDto> showDetCodeList(String mstCode){ return commonCodeMapper.listByMstCode(mstCode); }


    public int CountByOrderId(String orderId){ return orderDetailMapper.countByOrderId(orderId); }

    public List<RefundDto> selectOrderDetail(int orderDetailId){ return refundMapper.findByOrderDetailId(orderDetailId); }

    public int countByOrderId(String orderId){ return refundMapper.countByOrderId(orderId); }

    public int countAll(){return refundMapper.countAll();}

    public int countByUser(int userId){return refundMapper.countByUserId(userId);};

    private int imageRegister(MultipartFile imgFile,String imgPath,String code,int seq) throws Exception {
        int register;
        String[] contentsTypes = Objects.requireNonNull(imgFile.getContentType()).split("/");
        if(contentsTypes[0].equals("image")) {
            String fileName=code+"_"+System.currentTimeMillis()+"_"
                    +(int)(Math.random()*10000)+"."+contentsTypes[1];
            Path path= Paths.get(imgPath+"/"+fileName);
            imgFile.transferTo(path);
            ImageDto image=new ImageDto();
            image.setImgPath(fileName);
            image.setImgCode(code);
            image.setSeq(seq);
            register = imageMapper.insertOne(image);
        } else {
            throw new Exception("사진파일이 아닙니다.");
        }
        return register;
    }

}
