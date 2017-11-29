<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/27/17
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog | Create post</title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<nav>
    <button class="btn-menu" type="button" onclick="window.location.href='/'">Main</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/myPosts'">My posts</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/signIn'">Logout</button>
</nav>

<div class="container">
    <form class="center post-form" action="/createPost" method="post">
        <input type="text" name="title" placeholder="Title" value="${title}"/>
        <br/>
        <input type="text" name="description" placeholder="Description" value="${description}"/>
        <br/>
        <textarea type="text" name="text" placeholder="Text">${text}</textarea>
        <br/>
        <button type="submit" class="btn-primary">Create</button>

        <c:if test="${response.getResult() != 0}">
            <br/>
            <span class="center error">
                <c:out value="${response.getError().getErrmsg()}"/>
            </span>
        </c:if>
    </form>
</div>
</body>
</html>
