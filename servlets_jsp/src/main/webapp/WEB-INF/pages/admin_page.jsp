<%--
  Created by IntelliJ IDEA.
  User: koval-b
  Date: 09.02.22
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" uri="/WEB-INF/tld/MyTagDescriptor.tld" %>
<jsp:useBean id="userDao" class="com.koval.servlets_jsp.dao.JdbcUserDao" />

<html>
<head>
    <title>Admin page</title>
</head>
<body>

 <tag:MyTag users="${userDao.findAll()}"/>

</body>
</html>