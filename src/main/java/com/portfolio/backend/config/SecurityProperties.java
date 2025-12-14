package com.portfolio.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "security") // YAML의 'security' 키와 매핑
@Getter
@Setter
public class SecurityProperties {

    // YAML의 'whitelist' 키와 매핑됩니다.
    private List<String> whitelist;
}