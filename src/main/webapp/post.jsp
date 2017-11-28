<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/27/17
  Time: 7:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog |
        <c:if test="${response.getResult() == 0}">
            ${response.getData().get(0).getTitle()}
        </c:if>
    </title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<div>
    <a href="/">Main</a>

    <a href="/signIn">Logout</a>
</div>

<c:if test="${response.getResult() == 0}">
    <h1 class="post-header">
        <c:out value="${response.getData().get(0).getTitle()}"/>
    </h1>

    <div class="post-actions">
        <c:choose>
            <c:when test="${response.getData().get(0).isPosted()}">
                posted
            </c:when>
            <c:otherwise>
                isn't posted yet
            </c:otherwise>
        </c:choose>

        <a href="/editPost?id=${response.getData().get(0).getId()}">Edit</a>

        <a href="/editUpdaters?id=${response.getData().get(0).getId()}">Edit updaters</a>

        <a href="/deletePost?id=${response.getData().get(0).getId()}">Delete</a>
    </div>

    <div class="post-description">
        <c:out value="${response.getData().get(0).getDescription()}"/>
    </div>

    <div class="post-body">
        <c:out value="${response.getData().get(0).getText()}"/>
    </div>

    <h4 class="post-details">
        <a href="/user?id=${post.getCreator().getId()}">${response.getData().get(0).getCreator().getLogin()}</a>,
            ${response.getData().get(0).getCreatedAt()}
    </h4>
</c:if>

<c:if test="${response.getError() != null}">
    <span style="color: #f00">${response.getError().getErrormsg()}</span>
</c:if>
</body>
</html>
