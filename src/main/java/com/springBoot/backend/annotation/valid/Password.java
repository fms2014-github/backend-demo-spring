package com.springBoot.backend.annotation.valid;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
        message = "비밀번호 규칙에 벗어났습니다. 다시 확인해주세요.(숫자, 영문, 특수문자(@$!%*?&) 혼합 최소 8자리 ~ 15자리)")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "비밀번호 규칙에 벗어났습니다. 다시 확인해주세요.(숫자, 영문, 특수문자(@$!%*?&) 혼합 최소 8자리 ~ 15자리)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
