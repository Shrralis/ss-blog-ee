<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/29/17
  Time: 5:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Shrralis SS Blog | Deleting post</title>

    <link rel="stylesheet" type="text/css" href="styles/default.css">
</head>
<body>
<main class="container">
    <form class="center post-form">
        <h1>Are you sure you wanna completely delete the post?</h1>

        <button type="button" class="btn-primary red"
                onclick="window.location.href='/deletePost?id=${id}&confirm=true'">
            Delete
        </button>

        <button type="button" class="btn-default" onclick="window.location.href='/'">Cancel</button>
    </form>
</main>
</body>
</html>
