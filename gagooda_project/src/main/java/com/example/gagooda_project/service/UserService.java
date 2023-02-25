package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.UserDto;

import java.io.IOException;

public interface UserService {
    int register(UserDto user);
    UserDto login(String email, String pw);
    UserDto findpw(String email, String name);
    int modifyOne(UserDto user);
    UserDto selectOne(int userId);
    UserDto doubleCheck(String email, String pw);
    int delete(int userId);
    public String getToken() throws IOException;
    public String getCertifications(String imp_uid, String access_token) throws IOException;
}
