package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.mapper.CartMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService {
    private CartMapper cartMapper;

    public CartServiceImp(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDto selectByCartId(int cartId){
        return cartMapper.findById(cartId);
    }

    @Override
    public List<CartDto> cartList(int userId) {
        return cartMapper.listByUserId(userId);
    }

    @Override
    public CartDto selectOne(int userId, String optionCode) {
        return cartMapper.findByUserIdAndOptionCode(userId, optionCode);
    }

    @Override
    public int countCartItems(int userId) {
        int cnt = 0;
        cnt += cartMapper.countByUserId(userId);
        return cnt;
    }

    @Override
    public int removeOne(int cartId) {
        return cartMapper.deleteById(cartId);
    }

    @Override
    public int removeAll(int userId) {
        return cartMapper.deleteByUserId(userId);
    }

    @Override
    public int modifyOne(CartDto cart) {
        return cartMapper.updateOne(cart);
    }

    @Override
    public int registerOne(CartDto cart) {
        return cartMapper.insertOne(cart);
    }
}
