package com.springBoot.backend.security.filter;

import com.springBoot.backend.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Request Header에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰 유효성 검사
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // 3. 토큰에서 사용자 정보(이름) 추출
            String username = tokenProvider.getUsername(token);

            // 4. 인증 객체 생성 (실무에서는 UserDetailsService를 통해 DB에서 조회하는 것이 정석)
            // 여기서는 간단히 User 객체를 생성합니다.
            UserDetails userDetails = User.builder()
                    .username(username)
                    .password("") // 패스워드는 필요 없음
                    .roles("USER") // 토큰이나 DB에서 권한을 가져와야 함
                    .build();

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 5. SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    // 헤더에서 "Bearer " 부분을 제외하고 토큰만 가져옴
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            bearerToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("Authentication"))
            .findFirst().map(Cookie::getValue).orElse(null);

            return bearerToken;
        }
    }
}
