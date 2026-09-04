package com.springBoot.backend.service.impl;

import com.springBoot.backend.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String accountId) throws UsernameNotFoundException {
        return userInfoRepository.findByAccountId(accountId).orElseThrow(() -> new UsernameNotFoundException("일치하는 이메일 주소가 없습니다: " + accountId));
    }
}
