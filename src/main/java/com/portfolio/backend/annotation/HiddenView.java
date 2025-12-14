package com.portfolio.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 이 어노테이션이 붙은 Controller나 Method는
 * View 목록 조회 페이지에서 제외됩니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 메소드와 클래스 모두 붙일 수 있게 설정
@Retention(RetentionPolicy.RUNTIME)             // 실행 중(Runtime)에도 이 정보를 읽을 수 있어야 함 (필수!)
public @interface HiddenView {
}