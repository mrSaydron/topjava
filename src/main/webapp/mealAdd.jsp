<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/add" method="get">
    <a href="meals">Meals</a>
    <c:out value="${message}"/>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
        </tr>
        <tr>
            <td><input type="text" name="date"></td>
            <td><input type="text" name="description"></td>
            <td><input type="text" name="calories"></td>
            <td><input type="submit" value="add" name="action"></td>
        </tr>
    </table>
</form>
</body>
</html>
