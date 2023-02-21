package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.RefundDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface RefundService {
    public int registerOne(RefundDto refund, List<MultipartFile> imgFileList, String imgPath,String code,int seq) throws Exception;

    public List<RefundDto> showUserRefundList(int id, int period, String startDate, String endDate, String detCode, PagingDto paging);

    public List<RefundDto> showRefundList(Map<String, Object> searchFilter);

    public RefundDto selectOne(int id);

    public int modifyOne(RefundDto refund, String auth);
}
