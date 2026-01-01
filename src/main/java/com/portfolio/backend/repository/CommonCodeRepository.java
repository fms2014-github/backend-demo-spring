package com.portfolio.backend.repository;

import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonCodeId;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {

    @NonNull
    Optional<CommonCode> findById(CommonCodeId id);

    List<SelectCodeDto.Res> findBy();

    long count();
}
