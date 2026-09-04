package com.springBoot.backend.service.impl;

import com.springBoot.backend.dto.commonCode.InsertCodeDto;
import com.springBoot.backend.dto.commonCode.InsertGroupCodeDto;
import com.springBoot.backend.dto.commonCode.SelectCodeDto;
import com.springBoot.backend.dto.commonCode.SelectGroupCodeDto;
import com.springBoot.backend.entity.CommonCode;
import com.springBoot.backend.entity.CommonGroupCode;
import com.springBoot.backend.exception.CommonException;
import com.springBoot.backend.mapper.BaseMapper;
import com.springBoot.backend.repository.CommonCodeRepository;
import com.springBoot.backend.repository.CommonGroupCodeRepository;
import com.springBoot.backend.repository.spec.CommonGroupCodeSpecs;
import com.springBoot.backend.service.CommonCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {

    private final BaseMapper baseMapper;

    private final CommonCodeRepository commonCodeRepository;

    private final CommonGroupCodeRepository commonGroupCodeRepository;

    @Override
    @Transactional
    public int insertCode(InsertCodeDto.Req req) {
        log.debug("start insertCode");

        CommonCode commonCode = req.toCommonCodeEntity();

        if(commonCodeRepository.existsById(commonCode.getId())) {
            throw new CommonException(0, "키 중복");
        }

        commonCodeRepository.save(commonCode);

        return 0;
    }

    @Override
    @Transactional
    public int insertGroupCode(InsertGroupCodeDto.Req req) {
        log.debug("start insertGroupCode");
        CommonGroupCode entity = req.toCommonGroupCodeEntity();

        if(commonGroupCodeRepository.existsById(entity.getGroupCode())) {
            throw new CommonException(0, "키 중복");
        }
        commonGroupCodeRepository.save(entity);

        return 0;
    }

    @Override
    @Transactional
    public int updateGroupCode(CommonGroupCode commonGroupCode) {
        CommonGroupCode selectGroupCode = commonGroupCodeRepository.findById(10000).orElse(null);
        log.debug("##### selectGroupCode: {}", selectGroupCode);
        if(selectGroupCode != null) {
            selectGroupCode.setName(commonGroupCode.getName());
            selectGroupCode.setLastUpdateDate(Instant.now());
            return 0;
        }
        return -1;
    }

    @Override
    public List<CommonGroupCode> selectGroupCode(SelectGroupCodeDto.Req req) {

        log.debug(baseMapper.selectCommonCodeAll().toString());
        return commonGroupCodeRepository.findAll(CommonGroupCodeSpecs.isGroupCode(req.groupCode()));
    }

    @Override
    public List<SelectCodeDto.Res> selectCode() {
        return commonCodeRepository.findBy();
    }
}
