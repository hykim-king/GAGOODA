package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface RefundService {
    public int registerOne(RefundDto refund) throws Exception;

    public List<RefundDto> showUserRefundList(int id, int period, String startDate, String endDate, String detCode, PagingDto paging);
    public int showCountByUser(int userId, int period, String startDate, String endDate, String detCode);

    public List<RefundDto> showRefundList(Map<String, Object> searchFilter);
    public int countPageAll(Map<String,Object> searchFilter);

    public RefundDto selectOne(int id);

    public int modifyOne(RefundDto refund, String auth);
    public List<AddressDto> showAddressListByUserId(int userId);
    public AddressDto selectAddress(int addressId);
    public List<CommonCodeDto> showDetCodeList(String mstCode);
    public int countByOrderId(String orderId);
    public List<RefundDto> selectOrderDetail(int orderDetailId);
    public int countByRefundOrderId(String orderId);
    public int countAll();
    public int countByUser(int userId);
    public int registerAddress(AddressDto address);
    public OrderDetailDto selectOrderDetailByid(int orderDetailId);
}
