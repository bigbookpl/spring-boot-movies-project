<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Favourite list - warning</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    You are logged as: <sec:authentication property="principal.username"/><br>
    Your role: <sec:authentication property="principal.authorities"/>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <form action="/logout" method="post">
        <input class="fa fa-id-badge" type="submit" value="Log out">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</sec:authorize>
<h1>Movie already present in favourite list</h1>
<p>
    <a href="/movie/search">Search a movie</a>
</p>
<p>
    <a href="/movie/favourite">Favourite movies</a>
</p>
<p>
    <a href="/movie/top3">TOP 3</a>
</p>
</body>
</html>
