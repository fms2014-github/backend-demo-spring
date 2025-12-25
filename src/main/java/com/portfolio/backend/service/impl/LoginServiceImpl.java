package com.portfolio.backend.service.impl;

import com.portfolio.backend.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        return userInfoRepository.findByAccountId(accountId).orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + accountId));
    }
}
