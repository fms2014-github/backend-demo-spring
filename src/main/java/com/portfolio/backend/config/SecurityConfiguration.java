package com.portfolio.backend.config;

import com.portfolio.backend.service.HttpFormLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain jwtFilterChange(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/login").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
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
        http.securityMatcher("/**")
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/login-page", "/login-process", "/login-failure").permitAll()
                            .anyRequest().authenticated();
                })
                .userDetailsService(new HttpFormLoginService())
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
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
