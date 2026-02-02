package com.portfolio.backend.entity;

import com.portfolio.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

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
public class CommonCode extends BaseEntity {
    @EmbeddedId
    private CommonCodeId id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @CreationTimestamp
    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;
}