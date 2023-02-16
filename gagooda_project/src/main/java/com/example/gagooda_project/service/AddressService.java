package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;


import java.util.List;

public interface AddressService {
    int register(AddressDto address); //했고
    AddressDto selectOne(int addressId); //했고
    int modifyOne(AddressDto address); //했고
    AddressDto defaultAddress(int userId);
    List<AddressDto> addressList(int userId); // 했고 default address 는 빠진 주소 목록
    int modifyDefault(int userId);
    int removeOne(int addressId); // 했고


}
