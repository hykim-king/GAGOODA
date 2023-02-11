package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insertOne(UserDto dto);

    int deleteById(int userId);

    int updateOne(UserDto dto);

    UserDto findByEmailAndPw(String email, String pw);

    UserDto findByEmailAndName(String email, String name);

    int updatePw(String pw);

    List<UserDto> listAll();

//    List<UserDto> pageAll();  // PagingDto paging 필요
}
