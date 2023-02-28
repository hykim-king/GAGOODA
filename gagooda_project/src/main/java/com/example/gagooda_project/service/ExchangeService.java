package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;

import java.util.List;
import java.util.Map;

public interface ExchangeService {
    public int registerOne(ExchangeDto exchange) throws Exception;
    public List<ExchangeDto> showUserExchangeList(int userId, int period, String startDate, String endDate, String detCode, PagingDto paging);
    public int showCountByUser(int userId, int period, String startDate, String endDate, String detCode);
    public List<ExchangeDto> showExchangeList(Map<String, Object> searchFilter);
    public int countPageAll(Map<String,Object> searchFilter);
    public ExchangeDto selectOne(int id);
    public int modifyOne(ExchangeDto exchange, String auth);
    public List<AddressDto> showAddressListByUserId(int userId);
    public AddressDto selectAddress(int addressId);
    public List<CommonCodeDto> showDetCodeList(String mstCode);
    public int CountByOrderId(String orderId);
    public List<ExchangeDto> selectOrderDetail(int orderDetailId);
    public int countByOrderId(String orderId);
    public int countAll();
    public int countByUser(int userId);
    public int registerAddress(AddressDto address);
}
