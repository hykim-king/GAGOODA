package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<OrderDto> orderList(PagingDto paging, int userId, int dates);
    OrderDto selectOne(String orderId);
    List<OrderDetailDto> orderDetailList(String orderId);
    int register(OrderDto order, DeliveryDto delivery,List<String> cartList);
    DeliveryDto selectDelivery(String orderId);
    List<CartDto> userCartList(int userId);
    List<AddressDto> userAddressList(int userId);
    CartDto selectByCartId(int cartId);
    int modifyOne(String orderId,String oDet);
    List<CommonCodeDto> showDetCodeList(String mstCode);
    List<OrderDto> showOrderList(Map<String,Object> searchFilter);
    int countPageAll(Map<String,Object> searchFilter);
    int adminModify(List<String> orderIdList, List<String> oDetList);
    int countRefund(String orderId);
    OptionProductDto selectOptionProduct(String optionCode);
    int changeStock(int count,String optionCode,String orderId);
}
