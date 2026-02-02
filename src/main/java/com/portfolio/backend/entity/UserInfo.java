package com.portfolio.backend.entity;

import com.portfolio.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_info", schema = "fms2014", uniqueConstraints = {@UniqueConstraint(name = "user_info_pk",
        columnNames = {"account_id"})})
public class UserInfo extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 320)
    @NotNull
    @Column(name = "account_id", nullable = false, length = 320)
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String accountId;

    @Size(max = 256)
    @NotNull
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "last_login_time")
    private Instant lastLoginTime;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "login_fail_count", nullable = false)
    private Integer loginFailCount;

    @NotNull
    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @NotNull
    @CreationTimestamp
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private UserInfoDetail userInfoDetail;


    @NonNull
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @NonNull
    @Override
    public String getUsername() {
        return accountId;
    }

    public void setUserInfoDetail(UserInfoDetail userInfoDetail) {
        this.userInfoDetail = userInfoDetail;
        userInfoDetail.setUserInfo(this);
    }
}