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
    <title>Beauty Salon</title>
</head>
<body>
<navbar:navbar/>
<hr>

<select onchange="filterServiceByMaster(this);">
    <option selected value="0">All services</option>
    <c:forEach items="${masters}" var="master">
        <option value="<c:out value="${master.id}" />">
            <c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" />
        </option>
    </c:forEach>
</select>
<div id="services">
    <c:forEach items="${services}" var="service">
        <div>
            <p><c:out value="${service.name}" /></p>
            <p><c:out value="${service.description}" /></p>
        </div>
    </c:forEach>
</div>



<hr>

<select onchange="filterMasterByService(this);">
    <option selected value="0">All masters</option>
    <c:forEach items="${services}" var="service">
        <option value="<c:out value="${service.id}" />"><c:out value="${service.name}" /></option>
    </c:forEach>
</select>
<select onchange="orderBy();" id="order-column">
    <option selected value="0">Normal order</option>
    <option value="1">Order by name</option>
    <option value="2">Order by rate</option>
</select>
<select onchange="orderBy();" id="order-way">
    <option selected value="0">ASC</option>
    <option value="1">DESC</option>
</select>
<div id="masters">
    <c:forEach items="${masters}" var="master">
        <div>
            <p><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></p>
            <p><c:out value="${master.mark}" /></p>
        </div>
    </c:forEach>
</div>


<hr>
<script>
    function filterServiceByMaster(master) {
        console.log(master);
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("services").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "/salon/filterServices?id="+master.value , true);
        xhttp.send();
    }
    function filterMasterByService(service) {
        console.log(service);
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("masters").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "/salon/filterMasters?id="+service.value , true);
        xhttp.send();
    }
    function orderBy() {
        var column = document.getElementById("order-column").value;
        var way = document.getElementById("order-way").value;
        console.log(column);
        console.log(way);
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("masters").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "/salon/orderBy?id="+column+"&way="+way , true);
        xhttp.send();
    }
</script>
</body>
</html>