package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.AddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressMapperTest {

    @Autowired
    AddressMapper addressMapper;

    @Test
    void listByUserId() {
        List<AddressDto> addressList = addressMapper.listByUserId(1);
        System.out.println(addressList);
    }

    @Test
    void insertOne() {
        AddressDto address = new AddressDto();
        address.setUserId(1);
        address.setAname("한비집");
        address.setHome(false);
        address.setPostCode("12345");
        address.setAddress("인천시 서구 청라동");
        address.setAddressDetail("111동 111호");
        address.setReceiverName("유한비");
        address.setReceiverPhone("01012345678");
        address.setElevator(true);
        addressMapper.insertOne(address);
    }

    @Test
    void findById() {
        AddressDto address = addressMapper.findById(91);
        System.out.println(address);
    }

    @Test
    void updateOne() {
        AddressDto address = new AddressDto();
        address.setAddressId(91);
        address.setUserId(1);
        address.setAname("한비집");
        address.setHome(false);
        address.setPostCode("54321");
        address.setAddress("인천시 서구 청라동");
        address.setAddressDetail("123동 456호");
        address.setReceiverName("한비유");
        address.setReceiverPhone("01011112222");
        address.setElevator(true);
        addressMapper.updateOne(address);
        AddressDto check = addressMapper.findById(91);
        System.out.println(check);
    }

    @Test
    void findByUserIdAndNotHome() {
        List<AddressDto> addressList = addressMapper.findByUserIdAndNotHome(1);
        System.out.println(addressList);
    }

    @Test
    void findByUserIdAndHome() {
        AddressDto address = addressMapper.findByUserIdAndHome(1);
        System.out.println(address);
    }

    @Test
    void deleteById() {
        addressMapper.deleteById(91);
        AddressDto check = addressMapper.findById(91);
        System.out.println(check);
    }
    @Test
    void updateHome() {
        addressMapper.dismissHome(1);
    }
}