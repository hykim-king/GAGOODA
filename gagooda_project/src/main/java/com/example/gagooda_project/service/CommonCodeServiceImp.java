package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, String> showNames(String mstCode) {
        List<CommonCodeDto> commonCodeList = commonCodeMapper.listByMstCode(mstCode);
        Map<String, String> detDict = new HashMap<>();
        for (CommonCodeDto codeDto : commonCodeList) {
            detDict.put(codeDto.getDetCode(), codeDto.getDetName());
        }
        return detDict;
    }
}
