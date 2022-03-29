<%--
  Created by IntelliJ IDEA.
  User: BodyakoV
  Date: 04.02.2022
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<div class="login">
    <form action="login" method="post">
        <input type="text" placeholder="login" id="login" name="login">
        <input type="password" placeholder="password" id="password" name="password">
        <input class="button" type="submit" value="Sign In">
    </form>
    <p style="color:red; " align="center"><b>${param.message}</b></p>
</div>
</body>
</html>