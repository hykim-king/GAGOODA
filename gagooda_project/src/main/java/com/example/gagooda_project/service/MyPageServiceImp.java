package com.example.gagooda_project.service;


import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.ODetDto;
import com.example.gagooda_project.mapper.CartMapper;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageServiceImp implements MyPageService {
    private CartMapper cartMapper;
    private OrderMapper orderMapper;
    private CommonCodeMapper commonCodeMapper;

    public MyPageServiceImp(CartMapper cartMapper, OrderMapper orderMapper, CommonCodeMapper commonCodeMapper) {
        this.cartMapper = cartMapper;
        this.orderMapper = orderMapper;
        this.commonCodeMapper = commonCodeMapper;
    }

    @Override
    public List<CartDto> listByUserId(int userId) {
        return cartMapper.listByUserId(userId);
    }

    @Override
    public List<ODetDto> countByUserIdAndStatus(int userId) {
        return orderMapper.countByUserIdAndStatus(userId);
    }

    @Override
    public List<CommonCodeDto> showDetCodeList(String mstCode) {
        return commonCodeMapper.listByMstCode(mstCode);
    }
}
