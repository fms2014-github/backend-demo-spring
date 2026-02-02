package com.portfolio.backend.restController;

import com.portfolio.backend.dto.auth.JWTLoginDto;
import com.portfolio.backend.dto.auth.JWTSignUpDto;
import com.portfolio.backend.entity.UserInfo;
import com.portfolio.backend.entity.UserInfoDetail;
import com.portfolio.backend.exception.CommonException;
import com.portfolio.backend.repository.UserInfoRepository;
import com.portfolio.backend.security.provider.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JwtAuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JWTLoginDto.Res> login(@RequestBody JWTLoginDto.Req req, HttpServletRequest request, HttpServletResponse response){
        String accessToken;
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(req.email(), req.password());
            Authentication authentication = authenticationManager.authenticate(token);

            accessToken = jwtTokenProvider.createToken(authentication.getName(), "USER");

            Cookie authCookie = new Cookie("Authentication", accessToken);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false);
            authCookie.setPath("/");
            authCookie.setMaxAge(60 * 60 * 24); // 1일 유효
            response.addCookie(authCookie);
        } catch (UsernameNotFoundException e) {
            log.error("일치하는 이메일 없음");
            throw new CommonException(0, "등록된 이메일이 아닙니다.");
        } catch (BadCredentialsException e) {
            log.error("비밀번호 오류");
            throw new CommonException(0, "비밀번호가 맞지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Authentication failed: {}", e.getMessage());
            throw new CommonException(0, "인증 처리 중 오류");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JWTLoginDto.Res(accessToken));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<JWTSignUpDto.Res> signUp(@RequestBody JWTSignUpDto.Req req){

        UserInfo userInfo = new UserInfo();
        userInfo.setAccountId(req.accountId());
        userInfo.setPassword(passwordEncoder.encode(req.password()));

        UserInfoDetail userInfoDetail = new UserInfoDetail();
        userInfoDetail.setUserName(req.userName());
        userInfoDetail.setPhoneNumber(req.phoneNumber());
        userInfo.setUserInfoDetail(userInfoDetail);

        userInfoRepository.save(userInfo);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new JWTSignUpDto.Res("OK"));
    }
}
