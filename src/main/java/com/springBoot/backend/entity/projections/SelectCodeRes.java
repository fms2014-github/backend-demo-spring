package com.springBoot.backend.entity.projections;

import org.springframework.beans.factory.annotation.Value;

public interface SelectCodeRes {
    int getCode();
    String getName();

    @Value("#{target.groupCode.groupCode}")
    int getGroupCode();
}
