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
    <p> <a href="index.html">HOME</a> </p>
    <c:if test="${!empty message}">
        <p><c:out value="${message}"/></p>
    </c:if>
    <p> <a href="/add">ADD</a> </p>
    <table>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th colspan="2">Actions</th>
        </tr>
        <c:forEach var = "meal" items="${meals}">
            <tr class="exceed-${meal.exceed}">
                <th><c:out value="${meal.getDate()} ${meal.getTime()}"/></th>
                <th><c:out value="${meal.description}"/></th>
                <th><c:out value="${meal.calories}"/></th>
                <th><a href="<c:url value="/edit?id=${meal.id}"/>">edit</a></th>
                <th><a href="<c:url value="/remove?id=${meal.id}"/>">delete</a></th>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
