package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;
    private DeliveryMapper deliveryMapper;
    private CartMapper cartMapper;
    private AddressMapper addressMapper;
    private CommonCodeMapper commonCodeMapper;
    public OrderServiceImp(OrderMapper orderMapper,
                           OrderDetailMapper orderDetailMapper,
                           DeliveryMapper deliveryMapper,
                           CartMapper cartMapper,
                           AddressMapper addressMapper,
                           CommonCodeMapper commonCodeMapper
                           ){
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.deliveryMapper = deliveryMapper;
        this.cartMapper = cartMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
    }
    @Override
    public List<OrderDto> orderList(PagingDto paging, int userId,int dates) {
        int totalRows=orderMapper.count(paging,userId,dates);
        paging.setRows(4);
        paging.setOrderField("reg_date");
        paging.setTotalRows(totalRows);
        System.out.println("pagingDto: "+paging);
        return orderMapper.pageAll(paging,userId,dates);
    }

    @Override
    public OrderDto selectOne(String orderId) { return orderMapper.findById(orderId); }

    @Override
    public List<OrderDetailDto> orderDetailList(String orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    @Transactional
    @Override
    public int register(OrderDto order, DeliveryDto delivery, List<String> cartList) {
        int register = 0;
        int delete = 0;
        if(order != null){
            register = orderMapper.insertOne(order);
            if(order.getOrderDetailList() != null) {
                for (OrderDetailDto orderDetail : order.getOrderDetailList()) {
                    register += orderDetailMapper.insertOne(orderDetail);
                }
            }
        }
       if(delivery != null){
           register += deliveryMapper.insertOne(delivery);
           for(String cartNum: cartList){
               int cartId = Integer.parseInt(cartNum);
               delete += cartMapper.deleteById(cartId);
           }
       }
       if(delete > 0){
           return register;
       }else{
           throw new Error();
       }
    }

    @Override
    public DeliveryDto selectDelivery(String orderId) {
        return deliveryMapper.findByOrderId(orderId);
    }

    @Override
    public List<CartDto> userCartList(int userId) {
        return cartMapper.listByUserId(userId);
    }

    @Override
    public List<AddressDto> userAddressList(int userId) {
        return addressMapper.listByUserId(userId);
    }

    @Override
    public CartDto selectByCartId(int cartId) {
        return cartMapper.findById(cartId);
    }

    @Override
    public int modifyOne(String orderId, String oDet) {
        return orderMapper.updateStatus(orderId,oDet);
    }

    @Override
    public List<CommonCodeDto> showDetCodeList(String mstCode) {
        return commonCodeMapper.listByMstCode(mstCode);
    }

    @Override
    public List<OrderDto> showOrderList(Map<String, Object> searchFilter) {
        if(!searchFilter.get("oDet").equals("")){
            String oDet = searchFilter.get("oDet").toString();
            List<String> oList = new ArrayList<>(Arrays.asList(oDet.split(",")));
            System.out.println("oList: "+oList);
            String oDetF = "'"+String.join("','", oList)+"'";
            System.out.println("oDetF: "+oDetF);
            searchFilter.put("oDet", oDetF);
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
        int totalRows = orderMapper.countPageAll(searchFilter);
        pagingDto.setRows(10);
        pagingDto.setTotalRows(totalRows);
        if (pagingDto.getOrderField() == null){
            pagingDto.setOrderField("reg_date");
        }
        searchFilter.put("paging",pagingDto);
        return orderMapper.pageAdminAll(searchFilter);
    }

    @Override
    public int countPageAll(Map<String, Object> searchFilter) {
        return orderMapper.countPageAll(searchFilter);
    }

    @Transactional
    @Override
    public int adminModify(List<String> orderIdList, List<String> oDetList) {
        int register = 0;
        System.out.println("adminModify 서비스에 도달!");
        for (int i=0; i<orderIdList.size(); i++){
            for(int j=0; j<oDetList.size(); j++){
                if(i==j){
                    System.out.println("adminModify orderId: "+orderIdList.get(i));
                    System.out.println("adminModify oDet: "+oDetList.get(j));
                   register += orderMapper.updateStatus(orderIdList.get(i),oDetList.get(j));
                }
            }
        }
        System.out.println("adminModify register: "+register);
        return register;
    }



}
