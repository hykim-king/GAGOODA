package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CartDto;

import java.util.List;


public interface CartService {
    CartDto selectByCartId(int cartId);
    List<CartDto> cartList(int userId);
    CartDto selectOne(int userId, String optionCode);
    int countCartItems(int userId);
    int removeOne(int cartId);
    int removeAll(int userId);
    int modifyOne(CartDto cart);
    int registerOne(CartDto cart);
}
