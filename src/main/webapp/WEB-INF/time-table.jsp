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
    <title>Time-table</title>
</head>
<body>
<table>
    <tr>
        <th>Time</th>
        <th>Service</th>
        <th>Client</th>
        <th>Status</th>
        <th></th>
    </tr>
    <c:forEach items="${records}" var="record">
        <tr>
            <td><c:out value="${record.hour}" />:00</td>
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

</table>
</body>
</html>
