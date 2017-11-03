<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>

    <style type="text/css">
        .exceed-true {
            color: red;
        }
        .exceed-false {
            color: black;
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var = "meal" items="${meals}">
            <tr class="exceed-${meal.exceed}">
                <th><c:out value="${meal.getDate()} ${meal.getTime()}"/></th>
                <th><c:out value="${meal.description}"/></th>
                <th><c:out value="${meal.calories}"/></th>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
