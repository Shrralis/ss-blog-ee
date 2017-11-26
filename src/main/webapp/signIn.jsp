<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/26/17
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog | Sign in</title>
</head>
<body>
<div>
    <a href="/signUp">Register</a>
</div>

<form action="/signIn" method="post">
    <input type="text" name="login" placeholder="Login" value="${login}"/>
    <br/>
    <input type="password" name="password" placeholder="Password"/>
    <br/>
    <button type="submit">Sign in</button>
</form>

<span style="color: #F00;">${error}</span>
</body>
</html>
