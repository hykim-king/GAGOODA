package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeServiceImp implements ExchangeService {
    private ExchangeMapper exchangeMapper;
    private AddressMapper addressMapper;
    private CommonCodeMapper commonCodeMapper;
    private OrderMapper orderMapper;

    public ExchangeServiceImp(ExchangeMapper exchangeMapper,
                              AddressMapper addressMapper,
                              CommonCodeMapper commonCodeMapper,
                              OrderMapper orderMapper) {
        this.exchangeMapper = exchangeMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
        this.orderMapper = orderMapper;
    }
    @Override
    @Transactional
    public int registerOne(ExchangeDto exchange) throws Exception {
        int register = 0;
        List<ExchangeDto> checkExchangeList = null;
        // 주문 상세 단건 등록의 경우
        checkExchangeList = exchangeMapper.findByOrderDetailId(exchange.getOrderDetailId());
        OrderDto order = orderMapper.findById(exchange.getOrderId());
        if (checkExchangeList != null){ // db 조회가 되지만,
            for (ExchangeDto checkExchange : checkExchangeList){
                if(!checkExchange.getExDet().equals("ex1")){ // 상태가 rf1(요청 취소) 이외의 코드인 경우 실패
                    throw new RuntimeException();
                }
            }
        }
        register += exchangeMapper.insertOne(exchange);
        return register;
    }
    @Override
    public List<ExchangeDto> showUserExchangeList(int userId, int period, String startDate, String endDate, String detCode, PagingDto paging){
        if (startDate != null && startDate.equals(endDate)){
            startDate = startDate + "%";
            endDate = startDate;
        }
        int totalRows = exchangeMapper.countByUserIdAndDate(userId, period, startDate, endDate, detCode);
        paging.setTotalRows(totalRows);
        paging.setOrderField("reg_date");
        paging.setRows(5);
        return exchangeMapper.pageByUserIdAndDate(userId, period, startDate, endDate, detCode, paging);
    }
    @Override
    public int showCountByUser(int userId, int period, String startDate, String endDate, String detCode){
        if (startDate != null && startDate.equals(endDate)){
            startDate = startDate + "%";
            endDate = startDate;
        }
        return exchangeMapper.countByUserIdAndDate(userId, period, startDate, endDate, detCode);
    }
    @Override
    public List<ExchangeDto> showExchangeList(Map<String, Object> searchFilter){
        if(!searchFilter.get("exDet").equals("")){
            String rfDet = searchFilter.get("exDet").toString();
            List<String> exList = new ArrayList<>(Arrays.asList(rfDet.split(",")));
            String exDetF = "'"+String.join("','", exList)+"'";
            searchFilter.put("exDet", exDetF);
        }
        if(searchFilter.get("searchDiv").equals("all")){
            String allCol = "exchange_id OR user_id OR uname OR email OR phone OR order_detail_id OR order_id " +
                    "OR receiver_name OR receiver_phone";
            searchFilter.put("searchDiv", allCol);
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
        int totalRows = exchangeMapper.countPageAll(searchFilter);
        pagingDto.setRows(10);
        pagingDto.setTotalRows(totalRows);
        if (pagingDto.getOrderField() == null){
            pagingDto.setOrderField("reg_date");
        }
        searchFilter.put("paging",pagingDto);
        return exchangeMapper.pageAll(searchFilter);
    }
    @Override
    public int countPageAll(Map<String,Object> searchFilter){ return exchangeMapper.countPageAll(searchFilter); }
    @Override
    public ExchangeDto selectOne(int id){ return exchangeMapper.findById(id); }
    @Override
    public int modifyOne(ExchangeDto exchange, String auth){ return exchangeMapper.updateOne(exchange, auth); }
    @Override
    public List<AddressDto> showAddressListByUserId(int userId){
        return addressMapper.listByUserId(userId);
    }
    @Override
    public AddressDto selectAddress(int addressId){
        return addressMapper.findById(addressId);
    }
    @Override
    public List<CommonCodeDto> showDetCodeList(String mstCode){ return commonCodeMapper.listByMstCode(mstCode); }
    @Override
    public int CountByOrderId(String orderId){ return exchangeMapper.countByOrderId(orderId); }
    @Override
    public List<ExchangeDto> selectOrderDetail(int orderDetailId){ return exchangeMapper.findByOrderDetailId(orderDetailId); }
    @Override
    public int countByOrderId(String orderId){ return exchangeMapper.countByOrderId(orderId); }
    @Override
    public int countAll(){return exchangeMapper.countAll();}
    @Override
    public int countByUser(int userId){return exchangeMapper.countByUserId(userId);};
    @Override
    public int registerAddress(AddressDto address){return addressMapper.insertOne(address);}

//    @Override
//    public List<ExchangeDto> list(List<String> exDetList) {
//        return exchangeMapper.pageAll(exDetList);
//    }
//
//    @Override
//    public List<ExchangeDto> orderInDate(int id, int period) {
//        return exchangeMapper.pageByUserAndDate(id, period);
//    }
//
//    @Override
//    public int register(ExchangeDto exchange) {
//        return exchangeMapper.insertOne(exchange);
//    }
//
//    @Override
//    public int modify(ExchangeDto exchange) {
//        return exchangeMapper.updateOne(exchange);
//    }
//
//    @Override
//    public int countByUserIdAndOrderDetailId(int userId, int orderDetailId) {
//        return exchangeMapper.countByUserIdAndOrderDetailId(userId, orderDetailId);
//    }
//
//    @Override
//    public ExchangeDto findById(int id) {
//        return exchangeMapper.findById(id);
//    }
//
//    @Override
//    public AddressDto selectAddress(int addressId) {
//        return addressMapper.findById(addressId);
//    }
//
//    @Override
//    public List<AddressDto> showAddressListByUserId(int userId) {
//        return addressMapper.listByUserId(userId);
//    }
//
//    @Override
//    public List<CommonCodeDto> detCodeList(String  mstCode) {
//        return commonCodeMapper.listByMstCode(mstCode);
//    }
//
//    @Override
//    public ExchangeDto selectOne(int id) {
//        return exchangeMapper.findById(id);
//    }
}
