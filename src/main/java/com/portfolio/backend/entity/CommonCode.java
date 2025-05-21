package com.portfolio.backend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "common_code", schema = "fms2014", indexes = {
        @Index(name = "common_code_code_index", columnList = "code"),
        @Index(name = "common_code_create_date_index", columnList = "create_date"),
        @Index(name = "common_code_last_update_date_index", columnList = "last_update_date")
})
@ToString
public class CommonCode {
    @EmbeddedId
    private CommonCodeId id;

    @Column(name = "name", nullable = false)
    private String name;

    @ColumnDefault("current_timestamp()")
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @ColumnDefault("current_timestamp()")
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    @PrePersist
    public void prePersist() {
        if (createDate == null) {
            createDate = Instant.now();  // 기본값 설정
        }

        if (lastUpdateDate == null) {
            lastUpdateDate = Instant.now();  // 기본값 설정
        }
    }
}