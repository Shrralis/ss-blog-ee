<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/23/17
  Time: 7:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog</title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<nav>
    <button class="btn-menu" type="button" onclick="window.location.href='/createPost'">New post</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/myPosts'">My posts</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/signIn'">Logout</button>
</nav>

<main class="container">
    <c:if test="${response.getResult() == 0}">
        <c:forEach items="${response.getData()}" var="post">
            <div class="post">
                <h1 class="post-header">
                    <a href="/post?id=${post.getId()}">
                        <c:out value="${post.getTitle()}"/>
                    </a>
                </h1>

                <div class="post-actions">
                    <c:choose>
                        <c:when test="${post.isPosted()}">
                            posted
                        </c:when>

                        <c:otherwise>
                            isn't posted yet
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${access.get(post.getId()) || post.getCreator().getId().equals(user_id)}">
                        <a href="/editPost?id=${post.getId()}">Edit</a>
                    </c:if>

                    <c:if test="${post.getCreator().getId().equals(user_id) || \"ADMIN\".equals(scope)}">
                        <a href="/deletePost?id=${post.getId()}">Delete</a>
                    </c:if>
                </div>

                <div class="post-description">
                    <c:out value="${post.getDescription()}"/>
                </div>

                <div class="post-body short">
                    <c:out value="${post.getText()}"/>
                </div>

                <a href="/post?id=${post.getId()}">Read full</a>

                <h5 class="post-details">
                    <javatime:format value="${post.getCreatedAt()}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate"/>

                    <a href="/user?id=${post.getCreator().getId()}">${post.getCreator().getLogin()}</a>, ${parsedDate}
                </h5>
            </div>
        </c:forEach>
    </c:if>
</main>
</body>
</html>