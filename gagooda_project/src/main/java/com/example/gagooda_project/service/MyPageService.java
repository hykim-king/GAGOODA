package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.ODetDto;
import com.example.gagooda_project.dto.OrderDto;

import java.util.List;

public interface MyPageService {
    List<CartDto> listByUserId(int userId);
    List<ODetDto> countByUserIdAndStatus(int userId);
    List<CommonCodeDto> showDetCodeList(String mstCode);
    List<OrderDto> orderList(int userId, int dates);
}
