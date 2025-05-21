package com.portfolio.backend.service.impl;

import com.portfolio.backend.dto.commonCode.InsertCodeDto;
import com.portfolio.backend.dto.commonCode.InsertGroupCodeDto;
import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import com.portfolio.backend.dto.commonCode.SelectGroupCodeDto;
import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonGroupCode;
import com.portfolio.backend.exception.DuplicateKeyException;
import com.portfolio.backend.mapper.BaseMapper;
import com.portfolio.backend.repository.CommonCodeRepository;
import com.portfolio.backend.repository.CommonGroupCodeRepository;
import com.portfolio.backend.repository.spec.CommonGroupCodeSpecs;
import com.portfolio.backend.service.CommonCodeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    private final BaseMapper baseMapper;

    private final CommonCodeRepository commonCodeRepository;

    private final CommonGroupCodeRepository commonGroupCodeRepository;

    @Override
    @Transactional
    public int insertCode(InsertCodeDto.Req req) {
        log.info("start insertCode");

        CommonCode commonCode = req.toCommonCodeEntity();

        if(commonCodeRepository.existsById(commonCode.getId())) {
            throw new DuplicateKeyException("키 중복");
        } else {
            entityManager.persist(commonCode);
        }

        return 0;
    }

    @Override
    @Transactional
    public int insertGroupCode(InsertGroupCodeDto.Req req) {
        log.info("start insertGroupCode");
        CommonGroupCode entity = req.toCommonGroupCodeEntity();

        if(commonGroupCodeRepository.existsById(entity.getGroupCode())) {
            throw new DuplicateKeyException("키 중복");
        } else {
            entityManager.persist(entity);
        }

        return 0;
    }

    @Override
    @Transactional
    public int updateGroupCode(CommonGroupCode commonGroupCode) {
        CommonGroupCode selectGroupCode = commonGroupCodeRepository.findById(commonGroupCode.getGroupCode()).orElse(null);
        log.info("##### selectGroupCode: {}", selectGroupCode);
        if(selectGroupCode != null) {
            selectGroupCode.setName(commonGroupCode.getName());
            selectGroupCode.setLastUpdateDate(Instant.now());
            return 0;
        }
        return -1;
    }

    @Override
    public List<CommonGroupCode> selectGroupCode(SelectGroupCodeDto.Req req) {

        return commonGroupCodeRepository.findAll(CommonGroupCodeSpecs.isGroupCode(req));
    }

    @Override
    public List<SelectCodeDto.Res> selectCode() {
        return commonCodeRepository.findBy();
    }
}
