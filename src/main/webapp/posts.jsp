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
<a href="/createPost">
    New post
</a>

<c:if test="${response.getResult() == 0}">
    <c:forEach items="${response.getData()}" var="post">
        <div class="post">
            <div class="post-header">
                <c:out value="${post.getTitle()}"/>
            </div>

            <div class="post-description">
                <c:out value="${post.getDescription()}"/>
            </div>

            <div class="post-body">
                <c:out value="${post.getText()}"/>
            </div>

            <div class="post-details">
                <c:out value="${post.getCreator().getLogin()}"/>, <c:out value="${post.getCreatedAt()}"/>
            </div>
        </div>
    </c:forEach>
</c:if>
</body>
</html>
