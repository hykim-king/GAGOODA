package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.ExchangeDto;
import com.example.gagooda_project.dto.OrderDetailDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeServiceImp implements ExchangeService {
    private ExchangeMapper exchangeMapper;
    private AddressMapper addressMapper;
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;

    public ExchangeServiceImp(ExchangeMapper exchangeMapper, AddressMapper addressMapper, OrderMapper orderMapper, OrderDetailMapper orderDetailMapper) {
        this.exchangeMapper = exchangeMapper;
        this.addressMapper = addressMapper;
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public List<ExchangeDto> list(List<String> exDetList) {
        return exchangeMapper.pageAll(exDetList);
    }

    @Override
    public List<ExchangeDto> orderInDate(int id, int period) {
        return exchangeMapper.pageByUserAndDate(id, period);
    }

    @Override
    public int register(ExchangeDto exchange) {
        return exchangeMapper.insertOne(exchange);
    }

    @Override
    public int modify(ExchangeDto exchange) {
        return exchangeMapper.updateOne(exchange);
    }

    @Override
    public int countByUserIdAndOrderDetailId(int userId, int orderDetailId) {
        return exchangeMapper.countByUserIdAndOrderDetailId(userId, orderDetailId);
    }

    @Override
    public ExchangeDto findById(int id) {
        return exchangeMapper.findById(id);
    }

    @Override
    public AddressDto selectAddress(int addressId) {
        return addressMapper.findById(addressId);
    }

    @Override
    public OrderDto selectOrder(String orderId) {
        return orderMapper.findById(orderId);
    }

    @Override
    public List<OrderDetailDto> showOrderDetailListByUserId(String orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    @Override
    public List<AddressDto> showAddressListByUserId(int userId) {
        return addressMapper.listByUserId(userId);
    }
}
