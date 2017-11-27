<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/23/17
  Time: 7:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog</title>
</head>
<body>
<div>
    <a href="/createPost">New post</a>

    <a href="/signIn">Logout</a>
</div>

<c:if test="${response.getResult() == 0}">
    <c:forEach items="${response.getData()}" var="post">
        <div class="post" style="margin: 32px auto">
            <div class="post-header">
                <a href="/post?id=${post.getId()}">
                    <c:out value="${post.getTitle()}"/>
                </a>
            </div>

            <div class="post-description">
                <c:out value="${post.getDescription()}"/>
            </div>

            <div class="post-body">
                <c:out value="${post.getText()}"/>
                <br/>
                <br/>
                <a href="/post?id=${post.getId()}">Read full</a>
            </div>

            <div class="post-details">
                <a href="/user?id=${post.getCreator().getId()}">${post.getCreator().getLogin()}</a>,
                    ${post.getCreatedAt()}
            </div>
        </div>
    </c:forEach>
</c:if>
</body>
</html>
