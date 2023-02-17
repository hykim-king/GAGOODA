package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;
    private DeliveryMapper deliveryMapper;
    private CartMapper cartMapper;
    private AddressMapper addressMapper;
    private OrderServiceImp(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper,
                            DeliveryMapper deliveryMapper, CartMapper cartMapper, AddressMapper addressMapper){
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.deliveryMapper = deliveryMapper;
        this.cartMapper = cartMapper;
        this.addressMapper = addressMapper;
    }
    @Override
    public List<OrderDto> orderList(PagingDto paging, int userId) {
        int totalRows=orderMapper.count(paging,userId);
        paging.setTotalRows(totalRows);
        return orderMapper.pageAll(paging);
    }

    @Override
    public OrderDto selectOne(String orderId) { return orderMapper.findById(orderId); }

    @Override
    public List<OrderDetailDto> orderDetailList(String orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    public int register(OrderDto order, DeliveryDto delivery) {
        int register = orderMapper.insertOne(order);
        System.out.println(register);
        for(OrderDetailDto orderDetail: order.getOrderDetailList()){
            register += orderDetailMapper.insertOne(orderDetail);
        }
        System.out.println(register);
        register += deliveryMapper.insertOne(delivery);
        System.out.println(register);
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
