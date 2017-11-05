<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal edit</title>
</head>
<body>
<form action="/edit" method="get">
    <input type="hidden" name="id" value="${meal.id}">
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
            <td><input type="text" name="date" value="${meal.getDateTime()}"></td>
            <td><input type="text" name="description" value="${meal.description}"></td>
            <td><input type="text" name="calories" value="${meal.calories}"></td>
            <td><input type="submit" value="edit" name="action"></td>
        </tr>
    </table>
</form>
</body>
</html>
