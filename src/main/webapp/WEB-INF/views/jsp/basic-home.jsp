<%-- JSTL core 라이브러리를 사용하기 위해 태그 라이브러리를 선언합니다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
       <title>Please Log In</title>
    </head>
    <body>
       <h1>HTTP Basic Type Authentication</h1>
       <form action="${pageContext.request.scheme}://log:out@${pageContext.request.serverName}:${pageContext.request.serverPort}/basic/home" method="get">
           <input type="submit" value="Log out" />
       </form>
    </body>
</html>