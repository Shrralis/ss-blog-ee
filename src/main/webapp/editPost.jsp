<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/28/17
  Time: 1:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog | Edit post</title>
</head>
<body>
<div>
    <a href="/">Main</a>

    <a href="/signIn">Logout</a>
</div>

<c:if test="${response != null && response.getResult() == 0}">
    <form action="/editPost" method="post">
        <input type="hidden" name="id" value="${response.getData().get(0).getId()}"/>

        <input type="text" name="title" placeholder="Title" value="${response.getData().get(0).getTitle()}"/>
        <br/>
        <input type="text" name="description" placeholder="Description"
               value="${response.getData().get(0).getDescription()}"/>
        <br/>
        <textarea type="text" name="text" placeholder="Text">${response.getData().get(0).getText()}</textarea>
        <br/>
        <button type="submit">Edit</button>

        <a href="/setPosted?id=${response.getData().get(0).getId()}&posted=${!response.getData().get(0).isPosted()}">
            <c:choose>
                <c:when test="${!response.getData().get(0).isPosted()}">
                    Post
                </c:when>

                <c:otherwise>
                    Set as draft
                </c:otherwise>
            </c:choose>
        </a>
    </form>
</c:if>

<c:if test="${response.getResult() != 0}">
    <span style="color: #F00;">
        <c:out value="${reponse.getError().getErrormsg()}"/>
    </span>
</c:if>
</body>
</html>
