package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;

import java.util.List;
import java.util.Map;

public interface CommonCodeService {
    List<CommonCodeDto> showDets(String mstCode);
    Map<String, String> showNames(String mstCode);
}
