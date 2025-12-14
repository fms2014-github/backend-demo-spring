package com.portfolio.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@MapperScan(basePackages = {"com.portfolio.backend.mapper"})
public class DatabaseConfiguration {

}

