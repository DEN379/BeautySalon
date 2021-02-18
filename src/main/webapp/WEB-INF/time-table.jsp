<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>Time-table</title>
</head>
<body>
<div class="container">
<form action="${pageContext.request.contextPath}/timeTable" method="get">
    <input type="date" name="date" required>
    <input type="submit">
</form>


<table class="table table-bordered">
    <thead>
        <tr>
            <th scope="col">Time</th>
            <th scope="col">Service</th>
            <th scope="col">Client</th>
            <th scope="col">Status</th>
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
                    <form action="${pageContext.request.contextPath}/timeTable/updateStatus?id=${record.id}" method="post">
                        <input type="submit" value="Finished">
                    </form>
<%--                <a href=">Finished</a>--%>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
