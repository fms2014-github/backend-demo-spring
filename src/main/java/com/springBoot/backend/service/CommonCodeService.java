package com.springBoot.backend.service;

import com.springBoot.backend.dto.commonCode.InsertCodeDto;
import com.springBoot.backend.dto.commonCode.InsertGroupCodeDto;
import com.springBoot.backend.dto.commonCode.SelectCodeDto;
import com.springBoot.backend.dto.commonCode.SelectGroupCodeDto;
import com.springBoot.backend.entity.CommonGroupCode;

import java.util.List;

public interface CommonCodeService {
    int insertCode(InsertCodeDto.Req req);

    int insertGroupCode(InsertGroupCodeDto.Req req);

    int updateGroupCode(CommonGroupCode commonGroupCode);

    List<CommonGroupCode> selectGroupCode(SelectGroupCodeDto.Req req);

    List<SelectCodeDto.Res> selectCode();
}
