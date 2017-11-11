<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <form action="meals" method="get">
        <input type="hidden" name="usr" value="${usr}">
        <p>
            <label for="start_date">От даты </label>
            <input type="date" name="start_date" id="start_date">
        </p>
        <p>
            <label for="end_date">До даты </label>
            <input type="date" name="end_date" id="end_date">
        </p>
        <p>
            <label for="start_time">От времени </label>
            <input type="time" name="start_time" id="start_time">
        </p>
        <p>
            <label for="end_time">До времени </label>
            <input type="time" name="end_time" id="end_time">
        </p>
        <p>
            <input type="submit" value="Фильтр">
        </p>
    </form>

    <a href="meals?action=create&usr=<c:out value="${usr}"/>">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}&usr=${usr}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}&usr=${usr}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>