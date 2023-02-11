package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.AddressDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<AddressDto> listByUserId(int id);
    int insertOne(AddressDto dto);
    AddressDto findById(int id);  // address_id
    int updateOne(AddressDto dto);
    List<AddressDto> findByUserIdAndNotPrimary(int id);
    AddressDto findByUserIdAndPrimary(int id);
    int deleteByAddressId(int id);
    int findPrimaryYes(int id);  // user_id
    int updatePrimary(int id);  // user_id
}
