package com.portfolio.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
@ToString
public class CommonCodeId implements java.io.Serializable {
    private static final long serialVersionUID = 4898540130926293840L;
    @Column(name = "code", nullable = false)
    private Integer code;

    @Column(name = "group_code", nullable = false)
    private Integer groupCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommonCodeId entity = (CommonCodeId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.groupCode, entity.groupCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, groupCode);
    }

}