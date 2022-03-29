<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 10.02.22
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        let check = function() {
            if (document.getElementById('password').value === document.getElementById('confirm_password').value) {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').innerHTML = 'matching';
            } else {
                document.getElementById('message').style.color = 'red';
                document.getElementById('message').innerHTML = 'not matching';
            }
        }
    </script>
</head>
<body>
    <h1>Add user</h1>
    <form method="post" action="${pageContext.request.contextPath}/admin/add_user">
        Login: <input type="text" name="login" required/> <br/>
        <label>password :
            <input name="password" id="password" type="password" onkeyup='check();' required />
        </label>
        <br>
        <label>confirm password:
            <input type="password" name="confirm_password" id="confirm_password"  onkeyup='check();' required/>
            <span id='message'></span>
        </label><br>
        Email: <input type="email" name="email" required/> <br/>
        First name: <input type="text" name="firstName" required/> <br/>
        Last name: <input type="text" name="lastName" required/> <br/>
        Birthday: <input type="date" name="birthday" required/> <br/>
        Role:
        <select name="roleName">
            <c:forEach items="${roles}" var="role">
                <option value="${role.name}">${role.name}</option>
            </c:forEach>
        </select> <br/>
        <input type="submit" value="ok" />
        <input type="button" value="cancel" onclick="document.location.href='/admin/main_page'">
    </form>
</body>
</html>