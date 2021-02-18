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
    <title>Users</title>
</head>
<body>
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">id#</th>
        <th scope="col">User name</th>
        <th scope="col">Email</th>
        <th scope="col">Role</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${records}" var="record">
        <tr>
            <th scope="row"><c:out value="${record.id}" /></th>
            <td><c:out value="${record.user.firstName}" />
                <c:out value="${record.user.lastName}" /></td>
            <td><c:out value="${record.userMaster.firstName}" />
                <c:out value="${record.userMaster.lastName}" /></td>
            <td><c:out value="${record.service.name}" /></td>
            <td><c:out value="${record.status.value()}" /></td>
            <td><c:out value="${record.time}" /></td>
            <td><a href="${pageContext.request.contextPath}/records/edit?id=${record.id}">Details</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
