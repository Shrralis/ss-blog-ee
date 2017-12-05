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

    <link rel="stylesheet" type="text/css" href="${ctx}/styles/default.css">
</head>
<body>
<jsp:include page="parts/main-header.jsp"/>

<main class="container">
    <c:if test="${response.getResult() == 0}">
        <div class="post center">
            <c:if test="${response.getData().get(0).getImage() != null}">
                <div class="post-image"
                     style="background-image: url('${ctx}/getImage?id=${response.getData().get(0).getImage().getId()}'); background-repeat: no-repeat; background-size: cover">
                </div>
            </c:if>

            <div class="post-body">
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
                        <a href="${ctx}/editPost?id=${response.getData().get(0).getId()}">Edit</a>
                    </c:if>

                    <c:if test="${response.getData().get(0).getCreator().getId().equals(user_id)}">
                        <a href="${ctx}/editUpdaters?id=${response.getData().get(0).getId()}">Edit updaters</a>
                    </c:if>


                    <c:if test="${response.getData().get(0).getCreator().getId().equals(user_id) || \"ADMIN\".equals(scope)}">
                        <a href="${ctx}/deletePost?id=${response.getData().get(0).getId()}">Delete</a>
                    </c:if>
                </div>

                <div class="post-description">
                    <c:out value="${response.getData().get(0).getDescription()}"/>
                </div>

                <div class="post-text">
                    <c:out value="${response.getData().get(0).getText()}"/>
                </div>

                <h5 class="post-details">
                    <javatime:format value="${response.getData().get(0).getCreatedAt()}" pattern="yyyy-MM-dd HH:mm:ss"
                                     var="parsedDate"/>

                    <a href="${ctx}/user?id=${response.getData().get(0).getCreator().getId()}">
                            ${response.getData().get(0).getCreator().getLogin()}</a>, ${parsedDate}
                </h5>
            </div>
        </div>
    </c:if>

    <c:if test="${response.getError() != null}">
        <span>${response.getError().getErrormsg()}</span>
    </c:if>
</main>
</body>
</html>
