package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RefundServiceImp implements RefundService{
    RefundMapper refundMapper;
    AddressMapper addressMapper;
    CommonCodeMapper commonCodeMapper;
    OrderDetailMapper orderDetailMapper;
    OrderMapper orderMapper;

    public RefundServiceImp(RefundMapper refundMapper, AddressMapper addressMapper, CommonCodeMapper commonCodeMapper, OrderDetailMapper orderDetailMapper) {
        this.refundMapper = refundMapper;
        this.addressMapper = addressMapper;
        this.commonCodeMapper = commonCodeMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Transactional
    public int registerOne(RefundDto refund){
        int register = 0;
        RefundDto checkRefund;
        if(refund.getOrderDetailId() == -1){
            OrderDto order = orderMapper.findById(refund.getOrderId());
            for(int i = 0; i < order.getOrderDetailList().size(); i++){
                refund.setOrderDetailId(order.getOrderDetailList().get(i).getOrderDetailId());
                refund.setCancelAmount(order.getOrderDetailList().get(i).getPrice());
                checkRefund = refundMapper.findByOrderDetailId(refund.getOrderDetailId());
                if (checkRefund != null){
                    switch(checkRefund.getRfDet()){
                        case "rf0": case "rf2": case "rf3": case "rf4": case "rf5": case "rf6": case "rf7": case "rf8":
                            throw new Error();
                        case "rf1":
                            register += refundMapper.insertOne(refund);
                            break;
                    }
                }else{

                    register += refundMapper.insertOne(refund);
                }
            }
        }else{
            checkRefund = refundMapper.findByOrderDetailId(refund.getOrderDetailId());
            if (checkRefund != null){
                throw new Error();
            }else{
                register += refundMapper.insertOne(refund);
            }
        }
        return register;
    }

    public List<RefundDto> showUserRefundList(int id, int period, String startDate, String endDate, String detCode){
        return refundMapper.pageByUserIdAndDate(id, period, startDate, endDate, detCode);
    }

    public List<RefundDto> showRefundList(List<CommonCodeDto> rfDetList){ return refundMapper.pageAll(rfDetList); }

    public RefundDto selectOne(int id){ return refundMapper.findById(id); }

    public int modifyOne(RefundDto refund, String auth){ return refundMapper.updateOne(refund, auth); }

    public List<AddressDto> showAddressListByUserId(int userId){
        return addressMapper.listByUserId(userId);
    }

    public AddressDto selectAddress(int addressId){
        return addressMapper.findById(addressId);
    }

    public List<CommonCodeDto> showDetCodeList(String mstCode){ return commonCodeMapper.listByMstCode(mstCode); }

    public int showCountByUser(int userId){ return refundMapper.countByUserId(userId); }

    public int CountByOrderId(String orderId){ return orderDetailMapper.countByOrderId(orderId); }

    public RefundDto selectOrderDetail(int orderDetailId){ return refundMapper.findByOrderDetailId(orderDetailId); }

    public int countByOrderId(String orderId){ return refundMapper.countByOrderId(orderId); }

}
