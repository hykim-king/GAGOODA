package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.UserDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insertOne() {
        UserDto user = new UserDto();
        user.setEmail("kjjjk@ajjsd.com");
        user.setPw("1234");
        user.setUname("이창국");
        user.setNickname("창국스님");
        user.setPhone("01033553444");
        user.setEmailCheck(true);
        user.setCardName("국민은행");
        user.setCardNum("1234567812345678");
        user.setCvc("352");
        user.setCardYear("11");
        user.setCardMonth("12");
        user.setGDet("g1");
        user.setMsDet("ms1");
        user.setMdrDet("mdr0");
        userMapper.insertOne(user);
    }
    @Test
    void deleteById() {
        int deleteUser = userMapper.deleteById(19);
    }
    @Test
    void findById(){
        UserDto findUser = userMapper.findById(2);
        System.out.println(findUser);
    }
    @Test
    void findByEmailAndPw() {
        UserDto findEmailPw = userMapper.findByEmailAndPw("kkk@asdfasd.com","1234");
        System.out.println(findEmailPw);
    }
    @Test
    void findByEmailAndName() {
        UserDto findEmailName = userMapper.findByEmailAndName("kkk@asdfasd.com","1234");
        System.out.println(findEmailName);
    }
    @Test
    void updatePw() {
        userMapper.updatePw("4321",1);
    }
    @Test
    void updateOne() {
        UserDto updateUser = userMapper.findById(1);
        updateUser.setUname("김현섭");
        updateUser.setNickname("천하민");
        updateUser.setPhone("01043214321");
        updateUser.setEmailCheck(false);
        userMapper.updateOne(updateUser);
    }
    @Test
    void listAll() {
        List<UserDto> allUser = userMapper.listAll();
        System.out.println(allUser);
    }
}
