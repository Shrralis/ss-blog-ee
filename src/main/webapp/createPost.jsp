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
    <title>Shrralis SS Blog | Create post</title>
</head>
<body>
<div>
    <a href="/">Main</a>

    <a href="/signIn">Logout</a>
</div>

<form action="/createPost" method="post">
    <input type="text" name="title" placeholder="Title" value="${title}"/>
    <br/>
    <input type="text" name="description" placeholder="Description" value="${description}"/>
    <br/>
    <textarea type="text" name="text" placeholder="Text">${text}</textarea>
    <br/>
    <button type="submit">Create</button>
</form>

<c:if test="${response.getResult() != 0}">
    <span style="color: #F00;">
        <c:out value="${reponse.getError().getErrormsg()}"/>
    </span>
</c:if>
</body>
</html>
