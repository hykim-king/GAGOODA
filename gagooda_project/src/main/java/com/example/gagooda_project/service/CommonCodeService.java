package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;

import java.util.List;

public interface CommonCodeService {
    List<CommonCodeDto> showDets(String mstCode);
}
