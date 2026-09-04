package com.springBoot.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Spring Boot Demo", description = "Spring Boot 학습을 위한 서버", version = "alpha-0.0.1"),
        servers = {
                @Server(url = "http://localhost:8080", description = "서버 설명")
        }
)
public class SwaggerConfig implements WebMvcConfigurer {

    // 1. 정적 리소스(JS, CSS)가 외부에서 접근 가능하도록 경로 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**", "/css/**")
                .addResourceLocations("classpath:/static/js/", "classpath:/static/css/");
    }

    @Bean
    public SwaggerIndexTransformer swaggerIndexTransformer(
            SwaggerUiConfigProperties swaggerUiConfig,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            ObjectMapperProvider objectMapperProvider,
            SwaggerWelcomeCommon swaggerWelcomeCommon) {

        return new SwaggerIndexPageTransformer(
                swaggerUiConfig,
                swaggerUiOAuthProperties,
                swaggerWelcomeCommon,
                objectMapperProvider
                ) {

            @Override
            public Resource transform(HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain) throws IOException {
                // 1. 기본 HTML 가져오기
                Resource defaultResource = super.transform(request, resource, transformerChain);

                // 2. 내용 읽기
                String html = new String(defaultResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

                // 3. CSS 주입 (다크모드)
                String cssLink = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/SwaggerDark.css\" />";
                String javascriptSrc = "<script src=\"/js/swagger-theme.js\"></script>";
                String modifiedHtml = html.replace("</head>", cssLink + javascriptSrc + "</head>");


                // 4. 반환
                return new TransformedResource(defaultResource, modifiedHtml.getBytes());
            }
        };
    }
}