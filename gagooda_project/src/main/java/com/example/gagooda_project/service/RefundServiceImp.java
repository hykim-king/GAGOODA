package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.RefundDto;
import com.example.gagooda_project.mapper.RefundMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundServiceImp implements RefundService{
    RefundMapper refundMapper;
    public RefundServiceImp(RefundMapper refundMapper){
        this.refundMapper = refundMapper;
    }
    public int registerOne(RefundDto refund){ return refundMapper.insertOne(refund); }

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

}
