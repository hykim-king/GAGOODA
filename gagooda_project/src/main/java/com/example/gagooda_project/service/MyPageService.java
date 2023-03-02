package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;

import java.util.List;

public interface MyPageService {
    List<CartDto> listByUserId(int userId);

    List<ODetDto> countByUserIdAndStatus(int userId);

    List<CommonCodeDto> showDetCodeList(String mstCode);

    List<OrderDto> orderList(int userId, int dates);

    List<OrderDto> orderList(PagingDto paging, int userId, int dates);

    List<CartDto> cartList(PagingDto paging, int userId);
}
