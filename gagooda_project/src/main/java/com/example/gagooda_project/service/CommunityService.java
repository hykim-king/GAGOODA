package com.example.gagooda_project.service;


import com.example.gagooda_project.dto.CommunityDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityService {

    List<CommunityDto> communityList(PagingDto paging);
    CommunityDto selectOne(int commId);
    int register(List<MultipartFile> imgFileList, CommunityDto community, String imgPath);
}
