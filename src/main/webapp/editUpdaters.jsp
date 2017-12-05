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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog | Edit updaters</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/styles/default.css">
</head>
<body>
<jsp:include page="parts/main-header.jsp"/>

<main class="container">
    <div class="center">
        <c:if test="${response.getResult() == 0}">
            <c:forEach items="${response.getData()}" var="user">
                <div class="user">
                    <div class="name">
                        <c:out value="${user.getUserLogin()}"/>
                    </div>

                    <c:choose>
                        <c:when test="${user.isPostUpdater()}">
                            <button class="btn-primary red" type="button"
                                    onclick="window.location.href='${ctx}/editUpdaters?action=revoke&user_id=${user.getUserId()}&id=${id}'">
                                Revoke
                            </button>
                        </c:when>

                        <c:otherwise>
                            <button class="btn-primary green" type="button"
                                    onclick="window.location.href='${ctx}/editUpdaters?action=add&user_id=${user.getUserId()}&id=${id}'">
                                Grant
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${response.getError() != null}">
            <span class="error center">${response.getError().getErrmsg()}</span>
        </c:if>
    </div>
</main>
</body>
</html>
