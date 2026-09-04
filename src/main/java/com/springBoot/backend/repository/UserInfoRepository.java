package com.springBoot.backend.repository;

import com.springBoot.backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByAccountId(String accountId);
}
