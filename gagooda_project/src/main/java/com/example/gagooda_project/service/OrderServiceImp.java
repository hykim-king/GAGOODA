package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;
    private DeliveryMapper deliveryMapper;
    private CartMapper cartMapper;
    private AddressMapper addressMapper;
    public OrderServiceImp(OrderMapper orderMapper,
                           OrderDetailMapper orderDetailMapper,
                           DeliveryMapper deliveryMapper,
                           CartMapper cartMapper,
                           AddressMapper addressMapper){
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.deliveryMapper = deliveryMapper;
        this.cartMapper = cartMapper;
        this.addressMapper = addressMapper;
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

//    delete cart도 여기서 after registration if (register >0) => deleteCart
    @Transactional
    @Override
    public int register(OrderDto order, DeliveryDto delivery) {
        int register = 0;
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
       }
        return register;
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
}
