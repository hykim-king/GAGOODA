package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CommunityDto;
import com.example.gagooda_project.dto.PagingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper {
    int insertOne(CommunityDto community);
    int updateOne(CommunityDto community);
    int count(PagingDto paging);
    List<CommunityDto> listAll(PagingDto paging);
    CommunityDto findById(int commId);
}
