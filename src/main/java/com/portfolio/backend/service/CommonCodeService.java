package com.portfolio.backend.service;

import com.portfolio.backend.dto.commonCode.InsertCodeDto;
import com.portfolio.backend.dto.commonCode.InsertGroupCodeDto;
import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import com.portfolio.backend.dto.commonCode.SelectGroupCodeDto;
import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonGroupCode;

import java.util.List;

public interface CommonCodeService {
    int insertCode(InsertCodeDto.Req req);

    int insertGroupCode(InsertGroupCodeDto.Req req);

    int updateGroupCode(CommonGroupCode commonGroupCode);

    List<CommonGroupCode> selectGroupCode(SelectGroupCodeDto.Req req);

    List<SelectCodeDto.Res> selectCode();
}
