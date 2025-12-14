<%--
  Created by IntelliJ IDEA.
  User: fms20
  Date: 25. 12. 5.
  Time: 오후 4:04
  To change this template use File | Settings | File Templates.
--%>
<%-- JSTL core 라이브러리를 사용하기 위해 태그 라이브러리를 선언합니다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Please Log In(JSP)</title>
	</head>
	<body>
		<h1>Please Log In</h1>
		<div th:if="${param.error}">
			Invalid username and password.</div>
		<div th:if="${param.logout}">
			You have been logged out.</div>
        <div th:if="${param.login}">
			You have been login.</div>
        <div th:if="not ${param.login}">
            <form th:action="@{/login-process}" method="post">
                <div>
                    <input type="text" name="username" placeholder="Username"/>
                </div>
                <div>
                    <input type="password" name="password" placeholder="Password"/>
                </div>
                <input type="submit" value="Log in" />
            </form>
        </div>
        <div th:if="${param.login}">
            <form th:action="@{/logout}" method="post">
                <input type="submit" value="Log out" />
            </form>
        </div>
	</body>
</html>