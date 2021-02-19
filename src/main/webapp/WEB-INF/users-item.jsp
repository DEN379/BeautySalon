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
    <title>Users Item</title>
</head>
<body>
<navbar:navbar/>
<p><c:out value="${user.id}" /></p>
<p><c:out value="${user.firstName}" />
    <c:out value="${user.lastName}" /></p>
<p><c:out value="${user.email}" /></p>
<p><c:out value="${user.role.value()}" /></p>


<c:if test="${user.role.value() == \"Client\"}">
    <form action="${pageContext.request.contextPath}/admin/users/setMaster?id=${user.id}" method="post">
        <input type="submit" value="Set master">
    </form>
</c:if>
<input type="submit" value="Add service to master" id="add-service" onclick="addService();">
<div id="div-add-service" style="display: none">
    <c:if test="${user.role.value() == \"Master\"}">
        <form action="${pageContext.request.contextPath}/admin/users/setService?id=${user.id}" method="post" id="services">
            <div id="select-service"></div>
            <input type="number" name="price" min="1" max="10000">
            <input type="submit" value="Set master">
        </form>
    </c:if>
</div>

<script defer>
    function addService() {

        //e.preventDefault();
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
