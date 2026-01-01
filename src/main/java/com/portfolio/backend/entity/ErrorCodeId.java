package com.portfolio.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ErrorCodeId implements Serializable {
    private static final long serialVersionUID = -205299733097515103L;
    @NotNull
    @Column(name = "code", nullable = false)
    private Integer code;

    @NotNull
    @Column(name = "`group`", nullable = false)
    private Integer group;


}