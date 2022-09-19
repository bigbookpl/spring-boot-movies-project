<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Favourite movies</title>
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
<h1>Favourite movies</h1>
<table style="text-align:center">
    <tr>
        <th>No</th>
        <th>Title</th>
        <th>Plot</th>
        <th>Genre</th>
        <th>Director</th>
        <th>Poster</th>
    </tr>
    <c:forEach items="${movies}" var="movie" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${movie.title}</td>
            <td>${movie.plot}</td>
            <td>${movie.genre}</td>
            <td>${movie.director}</td>
            <td><img src="${movie.poster}" alt="movie poster"></td>
            <td><a href="/movie/delete/${movie.id}">Delete the movie from favourite movies list</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="/movie/search">Search movie</a>
</p>
<p>
    <a href="/movie/top3">TOP 3</a>
</p>
</body>
</html>
