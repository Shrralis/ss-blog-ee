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

    <link rel="stylesheet" type="text/css" href="${ctx}/styles/default.css">
</head>
<body>
<jsp:include page="parts/main-header.jsp"/>

<main class="container">
    <c:if test="${response.getResult() == 0}">
        <c:choose>
            <c:when test="${!response.getData().isEmpty()}">
                <c:if test="${word != null}">
                    Found: ${response.getCount()}
                </c:if>

                <c:forEach items="${response.getData()}" var="post">
                    <div class="post center">
                        <c:if test="${post.getImage() != null}">
                            <div class="post-image"
                                 style="background-image: url('${ctx}/getImage?id=${post.getImage().getId()}'); background-repeat: no-repeat; background-size: cover">
                            </div>
                        </c:if>

                        <div class="post-body">
                            <h1 class="post-header">
                                <a href="${ctx}/post?id=${post.getId()}">
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
                                    <a href="${ctx}/editPost?id=${post.getId()}">Edit</a>
                                </c:if>

                                <c:if test="${post.getCreator().getId().equals(user_id) || \"ADMIN\".equals(scope)}">
                                    <a href="${ctx}/deletePost?id=${post.getId()}">Delete</a>
                                </c:if>
                            </div>

                            <div class="post-description">
                                <c:out value="${post.getDescription()}"/>
                            </div>

                            <div class="post-text short">
                                <c:out value="${post.getText()}"/>
                            </div>

                            <a href="${ctx}/post?id=${post.getId()}">Read full</a>

                            <h5 class="post-details">
                                <javatime:format value="${post.getCreatedAt()}" pattern="yyyy-MM-dd HH:mm:ss"
                                                 var="parsedDate"/>

                                <a href="${ctx}/user?id=${post.getCreator().getId()}">${post.getCreator().getLogin()}</a>, ${parsedDate}
                            </h5>
                        </div>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <h1>
                    Found no posts
                </h1>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${response.getError() != null}">
        <div class="error center">
                ${response.getError().getErrmsg()}
        </div>
    </c:if>
</main>
<footer>
    <div class="center">
        <div>
            <c:if test="${page > 1}">
                <a href="?page=${page - 1}${word == null ? '' : '&word='}${word}">< Previous page</a>
            </c:if>
        </div>

        <div>
            <c:if test="${response.getCount() - page * max_post_count > 0}">
                <a href="?page=${page + 1}${word == null ? '' : '&word='}${word}">Next page ></a>
            </c:if>
        </div>
    </div>
</footer>
</body>
</html>
