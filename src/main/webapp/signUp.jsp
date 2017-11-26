<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/26/17
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog | Sign up</title>
</head>
<body>
<div>
    <a href="/signIn">Sign in</a>
</div>

<form action="/signUp" method="post">
    <input type="text" name="login" placeholder="Login" value="${login}"/>
    <br/>
    <input type="password" name="password" placeholder="Password"/>
    <br/>
    <button type="submit">Sign up</button>
</form>

<span style="color: #F00;">${error}</span>
</body>
</html>
