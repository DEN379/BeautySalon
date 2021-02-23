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
    <title>Users Item</title>
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
        <p><fmt:message key="id" bundle="${bundle}"/>:      <c:out value="${user.id}" /></p>
        <p><fmt:message key="user" bundle="${bundle}"/>:   <c:out value="${user.firstName}" />
            <c:out value="${user.lastName}" /></p>
        <p>Email:   <c:out value="${user.email}" /></p>
        <p><fmt:message key="role" bundle="${bundle}"/>:    <c:out value="${user.role.value()}" /></p>


        <c:if test="${user.role.value() == \"Client\"}">
            <form action="${pageContext.request.contextPath}/admin/users/setMaster?id=${user.id}" method="post">
                <input type="submit" value="<fmt:message key="setMaster" bundle="${bundle}"/>">
            </form>
        </c:if>
        <c:if test="${user.role.value() == \"Master\"}">
            <input type="submit" value="<fmt:message key="addServiceToMaster" bundle="${bundle}"/>" id="add-service" onclick="addService();">
        </c:if>
        <div id="div-add-service" style="display: none">
            <c:if test="${user.role.value() == \"Master\"}">
                <form action="${pageContext.request.contextPath}/admin/users/setService?id=${user.id}" method="post" id="services">
                    <div id="select-service"></div>
                    <input type="number" name="price" min="1" max="10000">
                    <input type="submit" value="<fmt:message key="addService" bundle="${bundle}"/>">
                </form>
            </c:if>
        </div>
    </div>
<script defer>
    function addService() {
        var add = document.getElementById("div-add-service");
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("select-service").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "/salon/admin/users/getService?id=${user.id}", true);
        xhttp.send();
        add.style.display = "block";
    }
</script>

</body>
</html>
