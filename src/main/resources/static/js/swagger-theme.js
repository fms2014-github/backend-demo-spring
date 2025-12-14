// DOM이 변경되는 것을 감지하여, Swagger UI가 다 그려지면 버튼을 추가하는 로직
document.addEventListener("DOMContentLoaded", function() {

    // 타겟 노드 (body 전체를 감시)
    const targetNode = document.body;

    // 감시 설정
    const config = { childList: true, subtree: true };

    // 콜백 함수 (DOM 변경 시 실행)
    const callback = function(mutationsList, observer) {
        // 1. 이미 버튼이 있는지 확인 (중복 추가 방지)
        if (document.getElementById("btn-dark-mode-toggle")) {
            return;
        }

        // 2. Topbar 찾기
        const topbar = document.getElementsByClassName("topbar-wrapper")[0];

        // Topbar가 생성되었으면 버튼 추가
        if (topbar) {
            createToggleButton(topbar);
            // 버튼을 추가했으므로 더 이상 감시할 필요 없음 (성능 최적화)
            // observer.disconnect(); // 만약 페이지 이동 시 버튼이 사라진다면 이 줄은 주석 처리 유지
        }
    };

    // 옵저버 생성 및 시작
    const observer = new MutationObserver(callback);
    observer.observe(targetNode, config);

    // 버튼 생성 함수
    function createToggleButton(topbar) {
        let toggleButton = document.createElement("button");
        toggleButton.id = "btn-dark-mode-toggle"; // ID 부여
        toggleButton.innerHTML = "🌙 Dark Mode";
        toggleButton.style.cssText = "border: none; background: none; color: #3b4151; font-weight: bold; cursor: pointer; margin-left: 20px; vertical-align: middle;";

        topbar.appendChild(toggleButton);

        // 초기 테마 적용
        const darkModeMediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
        let isDarkMode = darkModeMediaQuery.matches;
        applyTheme(isDarkMode, toggleButton);

        // 클릭 이벤트
        toggleButton.onclick = function() {
            isDarkMode = !isDarkMode;
            applyTheme(isDarkMode, toggleButton);
        };
    }

    function applyTheme(isDark, btn) {
        if (isDark) {
            document.body.classList.add("dark-mode");
            btn.innerHTML = "🌙 Dark Mode";
            btn.style.color = "#3b4151";
        } else {
            document.body.classList.remove("dark-mode");
            btn.innerHTML = "☀️ Light Mode";
            btn.style.color = "#ffffff";
        }
    }
});