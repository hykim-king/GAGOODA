package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonCodeServiceImp implements CommonCodeService {
    CommonCodeMapper commonCodeMapper;

    public CommonCodeServiceImp(CommonCodeMapper commonCodeMapper) {
        this.commonCodeMapper = commonCodeMapper;
    }

    @Override
    public List<CommonCodeDto> showDets(String mstCode) {
        return commonCodeMapper.listByMstCode(mstCode);
    }
}
