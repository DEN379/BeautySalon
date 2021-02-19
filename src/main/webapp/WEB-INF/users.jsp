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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>Users</title>
</head>
<body>
<navbar:navbar/>
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
    <c:forEach items="${users}" var="user">
        <tr>
            <th scope="row"><c:out value="${user.id}" /></th>
            <td><c:out value="${user.firstName}" />
                <c:out value="${user.lastName}" /></td>
            <td><c:out value="${user.email}" /></td>
            <td><c:out value="${user.role.value()}" /></td>
            <td><a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}">Edit</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="pages"><c:out value="${pages}" /></div>
<script defer>
    document.addEventListener("DOMContentLoaded", load);
    function load() {

        document.getElementById("pages").innerHTML = `${pages}`;
    }
</script>
</body>
</html>
