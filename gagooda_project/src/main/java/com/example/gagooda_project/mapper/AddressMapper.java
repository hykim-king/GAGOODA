package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.AddressDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<AddressDto> listByUserId(int userId);
    // int id -> userId
    int insertOne(AddressDto address);
    AddressDto findById(int id);  // address_id
    int updateOne(AddressDto address);
    List<AddressDto> findByUserIdAndNotHome(int userId);
    AddressDto findByUserIdAndHome(int userId);
    int deleteById(int id);
    // deleteByAddressId -> deleteById

    int dismissHome(int userId);  // user_id
    // 용도가...?
    // primary 해제용이면 dismissHome(int userId) query는 updateHome랑 같게,
    // 설정용이면 setHome(int userId, int id) //address_id
    // 추천합니당
}
