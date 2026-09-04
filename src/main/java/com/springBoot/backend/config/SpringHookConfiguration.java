package com.springBoot.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j(topic = "RAW_LOGGER")
@Configuration
public class SpringHookConfiguration {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String LOCAL_IP_ADDRESS = "127.0.0.1";
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String KEY_STORY_PROPERTY = "server.ssl.key-store";

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup(ApplicationReadyEvent event) {
        try {
            // 1. 서버 포트 가져오기
            Environment env = event.getApplicationContext().getEnvironment();
            String port = env.getProperty("server.port", "8080");
            String contextPath = env.getProperty("server.servlet.context-path", "");
            String protocol = HTTP_PROTOCOL;

            if (env.getProperty(KEY_STORY_PROPERTY) != null) {
                protocol = HTTPS_PROTOCOL;
            }

            String message = """
            
            ╔════════════════════════════════════════════════════════════════════════════════════╗
            
                🚀  Server is ready!
            
                %Local%%swagger%%Network%%NetworkSwagger%
            
            ╚════════════════════════════════════════════════════════════════════════════════════╝
            
            """.replace("%Local%", BOLD + " ➜ Local: " + protocol + "localhost:" + port + contextPath)
                .replace("%swagger%", "\n     ➜ Local Swagger: " + protocol + "localhost:" + port + contextPath + "/swagger-ui.html");

            if (!getLocalIpAddress().isEmpty()) {
                message = message.replace("%Network%", "\n     ➜ Network: " + protocol + getLocalIpAddress() + ":" + port + contextPath)
                        .replace("%NetworkSwagger%", "\n     ➜ Network Swagger: " + protocol + getLocalIpAddress() + ":" + contextPath + "/swagger-ui.html" + RESET);
            } else {
                message = message.replace("%Network%", "")
                        .replace("%NetworkSwagger%", "");
            }

            log.info(message);

        } catch (Exception e) {
            log.error("서버 호스트 정보 조회 실패{}", e.getMessage());
        }
    }

    // IP 추출 로직 (private 메소드로 내장)
    private String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // IPv4 주소만 선택 (192.168... 등)
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return LOCAL_IP_ADDRESS;
    }
}
