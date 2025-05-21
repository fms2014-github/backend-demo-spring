package com.portfolio.backend.repository;

import com.portfolio.backend.dto.commonCode.SelectGroupCodeDto;
import com.portfolio.backend.entity.CommonGroupCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommonGroupCodeRepository extends JpaRepository<CommonGroupCode, Integer>, JpaSpecificationExecutor<CommonGroupCode> {

    Optional<CommonGroupCode> findById(Integer code);

    List<CommonGroupCode> findAll(Specification<CommonGroupCode> spec);
}
