package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartMapper cartMapper;

    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }
    public CartDto selectByCartId(int cartId){
        return cartMapper.findById(cartId);
    }

    public List<CartDto> cartList(int userId) {
        return cartMapper.listByUserId(userId);
    }

    public CartDto selectOne(int userId, String optionCode) {
        return cartMapper.findByUserIdAndOptionCode(userId, optionCode);
    }

    public int countCartItems(int userId) {
        int cnt = 0;
        cnt += cartMapper.countByUserId(userId);
        return cnt;
    }

    public int removeOne(int cartId) {
        return cartMapper.deleteById(cartId);
    }

    public int removeAll(int userId) {
        return cartMapper.deleteByUserId(userId);
    }
}
