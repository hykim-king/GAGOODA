package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeServiceImp implements ExchangeService {
    private ExchangeMapper exchangeMapper;
    private AddressMapper addressMapper;
    private CommonCodeMapper commonCodeMapper;

    public ExchangeServiceImp(ExchangeMapper exchangeMapper, AddressMapper addressMapper, CommonCodeMapper commonCodeMapper) {
        this.exchangeMapper = exchangeMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
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
    public List<AddressDto> showAddressListByUserId(int userId) {
        return addressMapper.listByUserId(userId);
    }

    @Override
    public List<CommonCodeDto> detCodeList(String  mstCode) {
        return commonCodeMapper.listByMstCode(mstCode);
    }

    @Override
    public ExchangeDto selectOne(int id) {
        return exchangeMapper.findById(id);
    }
}
