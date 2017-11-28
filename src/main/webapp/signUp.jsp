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

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<div class="container">
    <form class="center sign-form" action="/signUp" method="post">
        <h2 class="header">
            Registration
        </h2>

        <input type="text" name="login" placeholder="Login" value="${login}" autocomplete="false"/>
        <br/>
        <input type="password" name="password" placeholder="Password"/>
        <br/>
        <button class="btn-primary" type="submit">Sign up</button>

        <button class="btn-default" type="button" onclick="window.location.href='/signIn'">Sign in</button>

        <span class="error">${error}</span>
    </form>
</div>
</body>
</html>
