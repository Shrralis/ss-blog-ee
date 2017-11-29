<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/27/17
  Time: 7:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog |
        <c:if test="${response.getResult() == 0}">
            ${response.getData().get(0).getTitle()}
        </c:if>
    </title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<nav>
    <button class="btn-menu" type="button" onclick="window.location.href='/'">Main</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/createPost'">New post</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/myPosts'">My posts</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/signIn'">Logout</button>
</nav>

<main class="container">
    <c:if test="${response.getResult() == 0}">
        <div class="post">
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

                <c:if test="${access || response.getData().get(0).getCreator().getId().equals(user_id)}">
                    <a href="/editPost?id=${post.getId()}">Edit</a>
                </c:if>

                <c:if test="${response.getData().get(0).getCreator().getId().equals(user_id)}">
                    <a href="/editUpdaters?id=${response.getData().get(0).getId()}">Edit updaters</a>
                </c:if>


                <c:if test="${response.getData().get(0).getCreator().getId().equals(user_id) || \"ADMIN\".equals(scope)}">
                    <a href="/deletePost?id=${post.getId()}">Delete</a>
                </c:if>
            </div>

            <div class="post-description">
                <c:out value="${response.getData().get(0).getDescription()}"/>
            </div>

            <div class="post-body">
                <c:out value="${response.getData().get(0).getText()}"/>
            </div>

            <h5 class="post-details">
                <javatime:format value="${response.getData().get(0).getCreatedAt()}" pattern="yyyy-MM-dd HH:mm:ss"
                                 var="parsedDate"/>

                <a href="/user?id=${response.getData().get(0).getCreator().getId()}">
                        ${response.getData().get(0).getCreator().getLogin()}</a>, ${parsedDate}
            </h5>
        </div>
    </c:if>

    <c:if test="${response.getError() != null}">
        <span style="color: #f00">${response.getError().getErrormsg()}</span>
    </c:if>
</main>
</body>
</html>
