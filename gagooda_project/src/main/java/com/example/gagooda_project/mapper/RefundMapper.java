package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface RefundMapper {
//  currentDate 는 현재 시간을 넘겨주며, period는 음수로 변환한 기간(defaul로 -7의 값)을 넘겨야 한다.
//  pagingDto가 생성되면 pagingDto를 넘겨준다.(02.11- 현재는 넘겨줄 수 없으므로 mapper.xml에서 임의로 값을 넣어줬음.
    List<RefundDto> pageByUserIdAndDate(int id, Date currentDate, int period);

    int insertOne(RefundDto refund);

    RefundDto findById(int id);

    List<RefundDto> pageAll();

    int updateOne(RefundDto refund);

}
