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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog | Edit post</title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<nav>
    <button class="btn-menu" type="button" onclick="window.location.href='/'">Main</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/myPosts'">My posts</button>

    <button class="btn-menu" type="button" onclick="window.location.href='/signIn'">Logout</button>
</nav>

<div class="container">
    <c:choose>
        <c:when test="${postResponse != null && postResponse.getResult() == 0}">
            <form class="center post-form" action="/editPost" enctype="multipart/form-data" method="post">
                <c:if test="${postResponse.getData().get(0).getImage() != null}">
                    <div class="post-image"
                         style="background-image: url('/getImage?id=${postResponse.getData().get(0).getImage().getId()}'); background-repeat: no-repeat; background-size: cover">
                        <button class="btn-default" type="button"
                                onclick="window.location.href='/deleteImage?id=${postResponse.getData().get(0).getImage().getId()}&post_id=${postResponse.getData().get(0).getId()}'">
                            Delete image
                        </button>
                    </div>
                </c:if>

                <input type="hidden" name="id" value="${postResponse.getData().get(0).getId()}"/>

                <input type="text" name="title" placeholder="Title"
                       value="${postResponse.getData().get(0).getTitle()}"/>
                <br/>
                <input type="text" name="description" placeholder="Description"
                       value="${postResponse.getData().get(0).getDescription()}"/>
                <br/>
                <textarea type="text" name="text"
                          placeholder="Text">${postResponse.getData().get(0).getText()}</textarea>
                <br/>
                <label for="image-chooser">Choose new image for the post (optional)</label>

                <input id="image-chooser" type="file" name="image">
                <br/>
                <button class="btn-primary" type="submit">Edit</button>

                <button class="btn-default" type="button"
                        onclick="window.location.href='/setPosted?id=${postResponse.getData().get(0).getId()}&posted=${!postResponse.getData().get(0).isPosted()}'">
                    <c:choose>
                        <c:when test="${!postResponse.getData().get(0).isPosted()}">
                            Post
                        </c:when>

                        <c:otherwise>
                            Set as draft
                        </c:otherwise>
                    </c:choose>
                </button>

                <c:if test="${error != null}">
                    <span class="center error">
                        <c:out value="${error}"/>
                    </span>
                </c:if>
            </form>
        </c:when>

        <c:otherwise>
            <span class="center error">
                <c:out value="${postReponse.getError().getErrormsg()}"/>
            </span>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
