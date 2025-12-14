package com.portfolio.backend.controller;

import com.portfolio.backend.annotation.HiddenView;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@HiddenView
@Controller
public class SiteMapAllListController {

    private final RequestMappingHandlerMapping handlerMapping;

    public SiteMapAllListController(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping("/")
    public String home() {
        // 루트로 들어오면 /view-map으로 이동시켜라
        return "redirect:/view-map";
    }

    @GetMapping("/view-map")
    public String showViewMap(Model model) {
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        List<ViewEndpointInfo> viewEndpoints = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod method = entry.getValue();

            // @RestController가 붙은 클래스(API)는 제외하고
            // 일반 @Controller(화면)만 수집
            if (method.getBeanType().isAnnotationPresent(RestController.class)) {
                continue;
            }

            // ==========================================
            // [추가된 로직] @HiddenView 어노테이션 체크
            // ==========================================

            // 2-1. 메소드에 @HiddenView가 붙어있는지 확인
            if (method.hasMethodAnnotation(HiddenView.class)) {
                continue; // 붙어있으면 목록에 담지 않고 건너뜀
            }

            // 2-2. 클래스 전체에 @HiddenView가 붙어있는지 확인
            // 컨트롤러 클래스 위에 붙이면 그 안의 모든 메소드가 숨겨짐
            if (method.getBeanType().isAnnotationPresent(HiddenView.class)) {
                continue;
            }
            // ==========================================

            // 스프링 기본 에러 컨트롤러 등 제외 (필요시 주석 해제)
            if (method.getBeanType().getName().contains("BasicErrorController")) continue;

            RequestMappingInfo info = entry.getKey();

            // URL 패턴 가져오기
            String url = info.getDirectPaths().isEmpty() ?
                         info.toString() : String.join(", ", info.getDirectPaths());

            viewEndpoints.add(new ViewEndpointInfo(
                    url,
                    method.getBeanType().getSimpleName(), // 컨트롤러 이름
                    method.getMethod().getName()          // 메소드 이름
            ));
        }

        model.addAttribute("endpoints", viewEndpoints);
        return "thymeleaf/site-map-all-list"; // 뷰 파일 위치 (thymeleaf 또는 jsp 경로)
    }

    // 데이터를 담을 간단한 DTO 클래스
    @Getter
    public static class ViewEndpointInfo {
        // Getter 메소드들 (Thymeleaf/JSP에서 접근용)
        private String url;
        private String controllerName;
        private String methodName;

        public ViewEndpointInfo(String url, String controllerName, String methodName) {
            this.url = url;
            this.controllerName = controllerName;
            this.methodName = methodName;
        }

    }
}
