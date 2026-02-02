package com.portfolio.backend.entity;

import com.portfolio.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "error_code", schema = "fms2014")
@DynamicInsert
public class ErrorCode extends BaseEntity {
    @EmbeddedId
    private ErrorCodeId id;

    @Size(max = 50)
    @NotNull
    @Column(name = "code_name", nullable = false, length = 50)
    private String codeName;

    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;

    @Size(max = 150)
    @NotNull
    @Column(name = "message", nullable = false, length = 150)
    private String message;

    @Size(max = 500)
    @NotNull
    @Column(name = "detail_message", nullable = false, length = 500)
    private String detailMessage;

    @NotNull
    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @NotNull
    @CreationTimestamp
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @NotNull
    @ColumnDefault("'Y'")
    @Column(name = "use_yn", nullable = false)
    private String useYn;

}