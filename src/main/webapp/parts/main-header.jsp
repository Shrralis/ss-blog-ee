<%--
  Created by IntelliJ IDEA.
  User: shrralis
  Date: 12/5/17
  Time: 6:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <button class="btn-menu" type="button" onclick="window.location.href='${ctx}/'">Main</button>

    <c:if test="${user.getScope().ordinal() > READER_ORDINAL}">
        <button class="btn-menu" type="button" onclick="window.location.href='${ctx}/createPost'">New post</button>

        <button class="btn-menu" type="button" onclick="window.location.href='${ctx}/myPosts'">My posts</button>
    </c:if>

    <form action="search">
        <input name="word" placeholder="Search by word" value="${word}">

        <button class="btn-menu" type="submit">
            Search
        </button>
    </form>

    <button class="btn-menu" type="button" onclick="window.location.href='${ctx}/signIn'">Logout</button>
</nav>
