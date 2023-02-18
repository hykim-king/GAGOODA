package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;

import java.util.List;

public interface OrderService {
    List<OrderDto> orderList(PagingDto paging, int userId);
    OrderDto selectOne(String orderId);
    List<OrderDetailDto> orderDetailList(String orderId);
    int register(OrderDto order, DeliveryDto delivery);
    DeliveryDto selectDelivery(String orderId);
    List<CartDto> userCartList(int userId);
    List<AddressDto> userAddressList(int userId);
    CartDto selectByCartId(int cartId);

}
