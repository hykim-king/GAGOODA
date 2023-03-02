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
    ExchangeMapper exchangeMapper;
    OrderMapper orderMapper;
    ImageMapper imageMapper;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public RefundServiceImp(RefundMapper refundMapper, AddressMapper addressMapper, CommonCodeMapper commonCodeMapper, OrderDetailMapper orderDetailMapper, OrderMapper orderMapper, ImageMapper imageMapper) {
        this.refundMapper = refundMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.orderMapper = orderMapper;
        this.imageMapper = imageMapper;
    }

    @Transactional
    public int registerOne(RefundDto refund) throws Exception {
        int register = 0;
        List<RefundDto> checkRefundList = null;
        // 주문 상세 단건 등록의 경우
        checkRefundList = refundMapper.findByOrderDetailId(refund.getOrderDetailId());
        if (checkRefundList != null){ // db 조회가 되지만,
            for (RefundDto checkRefund : checkRefundList){
                if(!checkRefund.getRfDet().equals("rf1")){ // 상태가 rf1(요청 취소) 이외의 코드인 경우 실패
                    throw new RuntimeException();
                }
                register +=1 ;
            }
        }
        register += refundMapper.insertOne(refund);
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
        if(!searchFilter.get("searchWord").equals("")){
            String keyword = "%"+searchFilter.get("searchWord")+"%";
            searchFilter.put("searchWord", keyword);
        }
        if(searchFilter.get("startDate").equals(searchFilter.get("endDate"))){
            String equalsDate = searchFilter.get("startDate").toString() + "%";
            searchFilter.put("startDate", equalsDate);
            searchFilter.put("endDate", equalsDate);
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

    public int countByOrderId(String orderId){ return orderDetailMapper.countByOrderId(orderId); }

    public List<RefundDto> selectOrderDetail(int orderDetailId){ return refundMapper.findByOrderDetailId(orderDetailId); }

    public int countByRefundOrderId(String orderId){ return refundMapper.countByOrderId(orderId); }

    public int countAll(){return refundMapper.countAll();}

    public int countByUser(int userId){return refundMapper.countByUserId(userId);};

    public int registerAddress(AddressDto address){return addressMapper.insertOne(address);}

    public OrderDetailDto selectOrderDetailByid(int orderDetailId){ return orderDetailMapper.findById(orderDetailId); }

}
