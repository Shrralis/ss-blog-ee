<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 11/27/17
  Time: 4:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shrralis SS Blog | Create post</title>
</head>
<body>
<div>
    <a href="/">Main</a>

    <a href="/signIn">Logout</a>
</div>

<form action="/createPost" method="post">
    <input type="text" name="title" placeholder="Title" value="${title}"/>
    <br/>
    <input type="text" name="description" placeholder="Description" value="${description}"/>
    <br/>
    <textarea type="text" name="text" placeholder="Text">${text}</textarea>
    <br/>
    <button type="submit">Create</button>
</form>

<span style="color: #F00;">${error}</span>
</body>
</html>
