<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 04.02.22
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
</head>
<body>

    <h1 align="center">Hello, ${userName}!</h1>

    <div style="text-align: center; font-size: x-large">
        Click <a href="<c:url value="/logout"/>">here</a> to logout
    </div>
</body>
</html>