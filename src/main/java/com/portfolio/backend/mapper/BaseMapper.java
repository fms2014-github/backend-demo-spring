package com.portfolio.backend.mapper;

import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMapper {
    List<SelectCodeDto.Res> selectCommonCodeAll();
}
