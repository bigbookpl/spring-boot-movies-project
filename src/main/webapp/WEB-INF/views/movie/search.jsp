<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Search a movie</title>
</head>
<body>
<h1>Search a movie</h1>
<form method="post">
        <span>Title: <input type="text" name="title"></span>
        <span>Year of release: <input type="number" name="yearOfRelease"></span>
    <input type="submit" value="Search a movie">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<p>
    <a href="/movie/favourite">Favourite movies</a>
</p>
<p>
    <a href="/movie/top3">TOP 3</a>
</p>
</body>
</html>
