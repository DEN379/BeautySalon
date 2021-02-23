<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <title>Time-table</title>
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="ua"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>

    <fmt:setBundle basename="localization" var="bundle"/>
</head>
<body>
    <navbar:navbar/>
    <div class="container" style="margin-top: 20px">
        <form action="${pageContext.request.contextPath}/master/timeTable" method="get">
            <input type="date" name="date" id="calendar" required>
            <input type="submit">
        </form>


        <table class="table table-bordered">
            <thead>
                <tr>
                    <th scope="col"><fmt:message key="time" bundle="${bundle}"/></th>
                    <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                    <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                    <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${records}" var="record">
                    <tr>
                        <th scope="row"><c:out value="${record.hour}" />:00</th>
                        <td><c:out value="${record.serviceMaster.service.name}" /></td>
                        <td><c:out value="${record.user.firstName}" /> <c:out value="${record.user.lastName}" /></td>
                        <td><c:out value="${record.status.value()}" /></td>
                        <td>
                            <c:if test="${record.status.value() == \"accepted\"}">
                                <form action="${pageContext.request.contextPath}/master/timeTable/updateStatus?id=${record.id}" method="post">
                                    <input type="submit" value="<fmt:message key="finished" bundle="${bundle}"/>">
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
<script defer>
    function getToday() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        today = mm + '/' + dd + '/' + yyyy;
        return today;
    }
    document.getElementById('calendar').valueAsDate = new Date();
</script>
</body>
</html>
