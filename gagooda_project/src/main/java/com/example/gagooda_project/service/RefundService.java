package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.RefundDto;

import java.util.List;
import java.util.Map;

public interface RefundService {
    public int registerOne(RefundDto refund);

    public List<RefundDto> showUserRefundList(int id, int period, String startDate, String endDate, String detCode);

    public List<RefundDto> showRefundList(Map<String, String> searchFilter);

    public RefundDto selectOne(int id);

    public int modifyOne(RefundDto refund, String auth);
}
