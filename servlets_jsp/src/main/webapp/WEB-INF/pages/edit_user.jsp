<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 07.02.22
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
    <script>
        let check = function () {
            if (document.getElementById('password').value ===
                document.getElementById('confirm_password').value) {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').innerHTML = 'matching';
            } else {
                document.getElementById('message').style.color = 'red';
                document.getElementById('message').innerHTML = 'not matching';
            }
        };
    </script>
</head>
<body>
    <h1>Edit user</h1>
    <form name="loginForm" method="post" action="${pageContext.request.contextPath}/admin/edit_page">
        Login: <input type="text" name="login" value="${userForEdit.login}" readonly/> <br/>
        <label>password :
            <input name="password" id="password" type="password" onkeyup='check();' />
        </label>
        <br>
        <label>confirm password:
            <input type="password" name="confirm_password" id="confirm_password"  onkeyup='check();' />
            <span id='message'></span>
        </label><br>
        Email: <input type="email" name="email"  value="${userForEdit.email}" required/> <br/>
        First name: <input type="text" name="firstName" value="${userForEdit.firstName}" required/> <br/>
        Last name: <input type="text" name="lastName" value="${userForEdit.lastName}" required/> <br/>
        Birthday: <input type="date" name="birthday" value="${userForEdit.birthday}" required/> <br/>
        Role:
        <select name="roleName" >
            <c:forEach items="${roles}" var="role">
                <option value="${role.name}" selected="${role}">${role.name}</option>
            </c:forEach>
        </select> <br/>
        <input type="submit" value="ok" />
        <input type="button" name="blah" value="cancel" onclick="document.location.href='/admin/main_page'">
    </form>
</body>
</html>