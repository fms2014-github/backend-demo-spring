package com.portfolio.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_info_detail", schema = "fms2014")
public class UserInfoDetail {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    @Size(max = 50)
    @Column(name = "user_name", length = 50)
    private String userName;

    @Size(max = 256)
    @Column(name = "address", length = 256)
    private String address;

    @Size(max = 256)
    @Column(name = "address_detail", length = 256)
    private String addressDetail;

    @Size(max = 12)
    @Column(name = "phone_number", length = 12)
    private String phoneNumber;

    @Column(name = "birthday")
    private LocalDate birthday;


}