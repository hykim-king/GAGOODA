package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.AddressMapper;
import com.example.gagooda_project.mapper.OrderDetailMapper;
import com.example.gagooda_project.mapper.OrderMapper;
import com.example.gagooda_project.mapper.RefundMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundServiceImp implements RefundService{
    RefundMapper refundMapper;
    AddressMapper addressMapper;
    OrderDetailMapper orderDetailMapper;
    OrderMapper orderMapper;

    public RefundServiceImp(RefundMapper refundMapper, AddressMapper addressMapper, OrderDetailMapper orderDetailMapper, OrderMapper orderMapper) {
        this.refundMapper = refundMapper;
        this.addressMapper = addressMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.orderMapper = orderMapper;
    }

    public int registerOne(RefundDto refund, AddressDto address){
        return refundMapper.insertOne(refund);
    }

    public List<RefundDto> showUserRefundList(int id, int period){
        if (period == 0){
            return refundMapper.pageByUserIdAndDate(id, 7);
        } else{
            return refundMapper.pageByUserIdAndDate(id, period);
        }
    }

    public List<RefundDto> showRefundList(List<String> rfDetList){ return refundMapper.pageAll(rfDetList); }

    public RefundDto selectOne(int id){ return refundMapper.findById(id); }

    public int modifyOne(RefundDto refund){ return refundMapper.updateOne(refund); }

    public List<AddressDto> showAddressListByUserId(int userId){
        return addressMapper.listByUserId(userId);
    }
    public List<OrderDetailDto> showOrderDetailListByOrderId(String orderId){
        return orderDetailMapper.findByOrderId(orderId);
    }

    public OrderDto selectOrder(String orderId){
        return orderMapper.findById(orderId);
    }

}
