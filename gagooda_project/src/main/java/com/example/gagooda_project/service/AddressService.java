package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;


import java.util.List;

public interface AddressService {
    int register(AddressDto address);
    AddressDto selectOne(int addressId);
    int modifyOne(AddressDto address);
    AddressDto defaultAddress(int addressId);
    List<AddressDto> addressList(int addressId); // default address 는 빠진 주소 목록
    int modifyDefault(int addressId);
    int removeOne(int addressId);


}
