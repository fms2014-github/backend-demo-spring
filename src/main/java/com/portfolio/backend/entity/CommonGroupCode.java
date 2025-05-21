package com.portfolio.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "common_group_code", schema = "fms2014", indexes = {
        @Index(name = "common_group_code_group_code_name_index", columnList = "group_code, name"),
        @Index(name = "common_group_code_create_date_index", columnList = "create_date"),
        @Index(name = "common_group_code_last_update_date_index", columnList = "last_update_date")
}, uniqueConstraints = {
        @UniqueConstraint(name = "common_group_code_group_code_name_uindex", columnNames = {"group_code", "name"})
})
@ToString
public class CommonGroupCode {
    @Id
    @Column(name = "group_code", nullable = false)
    private Integer groupCode;

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