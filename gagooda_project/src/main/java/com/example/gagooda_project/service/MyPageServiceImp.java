package com.example.gagooda_project.service;


import com.example.gagooda_project.dto.*;
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
    public List<OrderDto> orderList(PagingDto paging, int userId, int dates) {
        int totalRows=orderMapper.count(paging,userId,dates);
        paging.setRows(4);
        paging.setOrderField("reg_date");
        paging.setTotalRows(totalRows);
        System.out.println("pagingDto: "+paging);
        return orderMapper.pageAll(paging,userId,dates);
    }

    @Override
    public List<CartDto> cartList(PagingDto paging, int userId) {
        int totalRows = cartMapper.count(paging, userId);
        paging.setDirect("ASC");
        paging.setRows(4);
        paging.setOrderField("cart_id");
        paging.setTotalRows(totalRows);
        return cartMapper.pageAll(paging, userId);
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

    @Override
    public List<OrderDto> orderList(int userId, int dates) {
        return orderMapper.listByUserIdAndDate(userId, dates);
    }
}
