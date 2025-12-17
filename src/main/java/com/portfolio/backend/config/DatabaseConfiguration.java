package com.portfolio.backend.config;

import com.portfolio.backend.util.SymmetricCryptoUtil;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@MapperScan(basePackages = {"com.portfolio.backend.mapper"})
public class DatabaseConfiguration {

    private final SecurityProperties securityProps;
    private final DataSourceProperties dataSourceProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() throws Exception {
        String finalPassword;
        String rawPath = securityProps.getKeyFilePath();
        String rawPassword = dataSourceProperties.getPassword(); // 표준 클래스에서 값 가져오기

        Path keyPath = Paths.get(rawPath).toAbsolutePath().normalize();

        // 1. 키 파일 확인 및 복호화 로직
        if (!rawPath.isEmpty() && Files.exists(keyPath)) {
            log.info("🔐 보안 키 파일 감지됨. 복호화 시도. (Path: {})", keyPath);
            try {
                String base64Decoding = new String(Base64.getDecoder().decode(rawPassword));
                finalPassword = SymmetricCryptoUtil.decrypt(rawPath, base64Decoding);
                log.info("✅ 비밀번호 복호화 성공.");
            } catch (Exception e) {
                log.error("❌ 비밀번호 복호화 실패.", e);
                throw e;
            }
        } else {
            log.warn("⚠️ 보안 키 파일 없음. (탐색 위치: {})", keyPath);
            log.warn("⚠️ 현재 실행 디렉토리(User Dir): {}", System.getProperty("user.dir"));
            log.warn("⚠️ 설정된 비밀번호를 '평문'으로 간주합니다.");
            finalPassword = rawPassword;
        }

        // 2. DataSource 생성 (표준 빌더 패턴 활용!)
        // initializeDataSourceBuilder()는 url, username, driver 등을 자동으로 세팅해줍니다.
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class) // HikariCP 명시
                .password(finalPassword)      // 우리가 만든(복호화된) 비밀번호로 덮어쓰기
                .build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
        );
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        // 1. JPA의 EntityManagerFactory를 주입 (Spring Boot가 자동 생성한 것을 가져옴)
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        transactionManager.setDefaultTimeout(30);

        return transactionManager;
    }

}

