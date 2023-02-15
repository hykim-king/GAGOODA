package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertOne(UserDto user);

    int deleteById(int id);

    int updateOne(UserDto user);

    UserDto findById(int id);

    UserDto findByEmailAndPw(String email, String pw);

    UserDto findByEmailAndName(String email, String uname);

    List<UserDto> listAll();

//    List<UserDto> pageAll();  // PagingDto paging 필요
}
