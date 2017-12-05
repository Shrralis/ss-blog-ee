<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 12/4/17
  Time: 1:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog | Deleting post image</title>

    <link rel="stylesheet" type="text/css" href="${ctx}/styles/default.css">
</head>
<body>
<main class="container">
    <form class="center post-form">
        <h1>Are you sure you wanna completely delete the image of the post?</h1>

        <button type="button" class="btn-primary red"
                onclick="window.location.href='${ctx}/deleteImage?id=${id}&post_id=${post_id}&confirm=true'">
            Delete
        </button>

        <button type="button" class="btn-default" onclick="window.location.href='${ctx}/post?id=${post_id}'">
            Cancel
        </button>
    </form>
</main>
</body>
</html>
