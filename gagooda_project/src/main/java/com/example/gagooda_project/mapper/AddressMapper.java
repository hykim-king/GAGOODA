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
    List<AddressDto> findByUserIdAndNotPrimary(int userId);
    AddressDto findByUserIdAndPrimary(int userId);
    int deleteById(int id);
    // deleteByAddressId -> deleteById
    int countPrimary(int userId);  // user_id
    //findPrimaryYes -> countPrimary
    // 용도가..? user에게 primary address가 있는지 확인하기 위해서..?
    // 만약 그렇다면 그냥 findByUserIdAndPrimary만 있어도 될거 같아요
    // 저걸 돌리면 null로 들어올거고 그러면 지정한 user에게 primary address는
    // 없다는 뜻이 됩니다.
    int updatePrimary(int userId);  // user_id
    // 용도가...?
    // primary 해제용이면 dismissPrimary(int userId) query는 updatePrimary랑 같게,
    // 설정용이면 setPrimary(int userId, int id) //address_id
    // 추천합니당
}
