package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.RefundDto;

import java.util.List;

public interface RefundService {
    public int registerOne(RefundDto refund, AddressDto address);

    public List<RefundDto> showUserRefundList(int id, int period);

    public List<RefundDto> showRefundList(List<String> rfDetList);

    public RefundDto selectOne(int id);

    public int modifyOne(RefundDto refund);
}
