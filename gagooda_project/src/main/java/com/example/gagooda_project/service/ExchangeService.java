package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;

import java.util.List;

public interface ExchangeService {
    List<ExchangeDto> list(List<String> exDetList);

    List<ExchangeDto> orderInDate(int id, int period);

    int register(ExchangeDto exchange);

    int modify(ExchangeDto exchange);

    int countByUserIdAndOrderDetailId(int userId, int orderDetailId);

    ExchangeDto findById(int id);

    AddressDto selectAddress(int addressId);

    OrderDto selectOrder(String orderId);

    List<OrderDetailDto> showOrderDetailListByUserId(String orderId);

    List<AddressDto> showAddressListByUserId(int userId);
}
