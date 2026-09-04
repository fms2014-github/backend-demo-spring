package com.springBoot.backend.config;

import com.springBoot.backend.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SecurityProperties securityProperties;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserDetailsService userDetailsService;

    @Bean
    @Order(1)
    public SecurityFilterChain jwtFilterChange(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> {
                authorize.requestMatchers("/api/login", "/api/sign-up").permitAll()
                        .anyRequest().authenticated();
            })
            .sessionManagement((session) -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    // 로그인 페이지(HTML)로 보내는 대신 401 에러를 전송
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 권한 부족 시 403 에러 전송
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Forbidden");
                })
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);;
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain httpBasicFilterChange(HttpSecurity http) throws Exception {
        http.securityMatcher("/basic/**")
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().authenticated();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> {
                    basic.authenticationEntryPoint((request, response, authException) -> {
                        log.info("HTTP Basic Auth required: {}", authException.getMessage());

                        response.setContentType("text/plain; charset=UTF-8");
                        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"My Realm\"");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().println("HTTP Status 401 - " + authException.getMessage());
                    });
                })
                .logout(logout -> {
                    logout.logoutUrl("/basic/logout")
                            .logoutSuccessUrl("/basic/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .logoutSuccessHandler((request, response, authException) -> {
                                response.setContentType("text/plain; charset=UTF-8");
                                response.setHeader("WWW-Authenticate", "Basic realm=\"My App\"");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().println("HTTP Status 401 - 자격 증명에 실패하였습니다.");
                            });
                });
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain httpFormFilterChange(HttpSecurity http) throws Exception {

        String[] whitelistArray = securityProperties.getWhitelist().toArray(new String[0]);

        http.securityMatcher("/**")
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(whitelistArray).permitAll()
                            .anyRequest().authenticated();
                })
                .userDetailsService(userDetailsService)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/login-page").permitAll()
                            .loginProcessingUrl("/login-process")
                            .defaultSuccessUrl("/login-page?login=true", true)
                            .failureUrl("/login-page?error=true");
                })
                .logout(formLogout -> {
                    formLogout.logoutUrl("/logout").permitAll()
                            .logoutSuccessUrl("/login-page?logout=true");
                });
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
