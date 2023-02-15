package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService{
    private AddressMapper addressMapper;

    public AddressServiceImp(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }
    @Override
    public int register(AddressDto address) {
        return addressMapper.insertOne(address);
    }

    @Override
    public AddressDto selectOne(int addressId) {
        return addressMapper.findById(addressId);
    }

    @Override
    public int modifyOne(AddressDto address) { return addressMapper.updateOne(address); }

    @Override
    public AddressDto defaultAddress(int addressId) {
        return null;
    }

    @Override
    public List<AddressDto> addressList(int userId) {
        return addressMapper.listByUserId(userId);
    }

    @Override
    public int modifyDefault(int addressId) {
        return 0;
    }

    @Override
    public int removeOne(int addressId) {
        return 0;
    }
}
