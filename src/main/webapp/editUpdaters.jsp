<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/28/17
  Time: 5:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog | Edit updaters</title>
</head>
<body>
<div>
    <a href="/">Main</a>

    <a href="/signIn">Logout</a>
</div>

<c:if test="${response.getResult() == 0}">
    <c:forEach items="${response.getData()}" var="user">
        <div class="user">
            <c:out value="${user.getLogin()}"/>

            <a href="/editUpdaters?action=add&user_id=${user.getId()}&id=${id}">Grant</a>

            <a href="/editUpdaters?action=revoke&user_id=${user.getId()}&id=${id}">Revoke</a>
        </div>
    </c:forEach>
</c:if>

<c:if test="${response.getError() != null}">
    <span style="color: #f00">${response.getError().getErrormsg()}</span>
</c:if>
</body>
</html>
