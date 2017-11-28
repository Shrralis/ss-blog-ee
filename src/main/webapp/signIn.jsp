<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<div class="container">
    <form class="center sign-form" action="/signIn" method="post">
        <h2 class="header">
            Authorization
        </h2>

        <input type="text" name="login" placeholder="Login" value="${login}" autocomplete="true"/>
        <br/>
        <input type="password" name="password" placeholder="Password"/>
        <br/>
        <button class="btn-primary" type="submit">Sign in</button>

        <button class="btn-default" type="button" onclick="window.location.href='/signUp'">Register</button>

        <span class="error">${error}</span>
    </form>
</div>
</body>
</html>
