package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundServiceImp implements RefundService{
    RefundMapper refundMapper;
    AddressMapper addressMapper;
    CommonCodeMapper commonCodeMapper;

    public RefundServiceImp(RefundMapper refundMapper, AddressMapper addressMapper, CommonCodeMapper commonCodeMapper) {
        this.refundMapper = refundMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
    }

    public int registerOne(RefundDto refund){
        return refundMapper.insertOne(refund);
    }

    public List<RefundDto> showUserRefundList(int id, int period, String startDate, String endDate, String detCode){
        return refundMapper.pageByUserIdAndDate(id, period, startDate, endDate, detCode);
    }

    public List<RefundDto> showRefundList(List<String> rfDetList){ return refundMapper.pageAll(rfDetList); }

    public RefundDto selectOne(int id){ return refundMapper.findById(id); }

    public int modifyOne(RefundDto refund){ return refundMapper.updateOne(refund); }

    public List<AddressDto> showAddressListByUserId(int userId){
        return addressMapper.listByUserId(userId);
    }

    public AddressDto selectAddress(int addressId){
        return addressMapper.findById(addressId);
    }

    public List<CommonCodeDto> showDetCodeList(String mstCode){ return commonCodeMapper.listByMstCode(mstCode); }

}
