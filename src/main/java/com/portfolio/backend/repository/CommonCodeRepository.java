package com.portfolio.backend.repository;

import com.portfolio.backend.dto.commonCode.SelectCodeDto;
import com.portfolio.backend.entity.CommonCode;
import com.portfolio.backend.entity.CommonCodeId;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeId> {

    @NonNull
    Optional<CommonCode> findById(@NonNull CommonCodeId id);

    CommonCode save(@NonNull CommonCode commonCode);

    List<SelectCodeDto.Res> findBy();

    long count();
}
