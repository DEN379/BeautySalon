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
    <title>Users</title>
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
    <div class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="id" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="user" bundle="${bundle}"/></th>
                <th scope="col">Email</th>
                <th scope="col"><fmt:message key="role" bundle="${bundle}"/></th>
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
                        <td><a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}">
                            <fmt:message key="edit" bundle="${bundle}"/></a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div id="pages"><c:out value="${pages}" /></div>
    </div>
<script defer>
    document.addEventListener("DOMContentLoaded", load);
    function load() {

        document.getElementById("pages").innerHTML = `${pages}`;
    }
</script>
</body>
</html>
