<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
    <h3><spring:message code="meal.title"/></h3>

        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-horizontal" id="filter">
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="startDate"><spring:message code="meal.startDate"/>:</label>
                        <div class="col-sm-3">
                            <input class="form-control" id="startDate" type="date" name="startDate" value="${param.startDate}">
                        </div>
                        <label class="control-label col-sm-3" for="endDate"><spring:message code="meal.endDate"/>:</label>
                        <div class="col-sm-3">
                            <input class="form-control" id="endDate" type="date" name="endDate" value="${param.endDate}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3"for="startTime"><spring:message code="meal.startTime"/>:</label>
                        <div class="col-sm-3">
                            <input class="form-control col-sm-3" id="startTime" type="time" name="startTime" value="${param.startTime}">
                        </div>
                        <label class="control-label col-sm-3" for="endTime"><spring:message code="meal.endTime"/>:</label>
                        <div class="col-sm-3">
                            <input class="form-control col-sm-3" id="endTime"type="time" name="endTime" value="${param.endTime}">
                        </div>
                    </div>
                    <div class="col-sm-10"></div>
                    <div class="col-sm-2">
                        <a class="btn btn-primary" type="button" onclick="aplyFilter()">
                            <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                        </a>
                        <a class="btn btn-danger" type="button" onclick="cliarFilter()">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <%--<a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="common.add"/>
        </a>--%>

        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="common.add"/>
        </button>

        <table class="table table-striped display" id="datatable">
            <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
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
                    <td><a><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                    <td><a class="delete" id="${meal.id}"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h2 class="modal-title">
                    <spring:message code="meal.edit"/>
                </h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="dateTime"><spring:message code="meal.dateTime"/>:</label>
                        <div class="col-sm-9">
                            <input class="form-control" id="dateTime" type="datetime-local" name="dateTime" placeholder="<spring:message code="meal.dateTime"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="description"><spring:message code="meal.description"/>:</label>
                        <div class="col-sm-9">
                            <input class="form-control" id="description" type="text" name="description" placeholder="<spring:message code="meal.description"/>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="calories"><spring:message code="meal.calories"/>:</label>
                        <div class="col-sm-9">
                            <input class="form-control" id="calories" type="number" name="calories" placeholder="1000">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>