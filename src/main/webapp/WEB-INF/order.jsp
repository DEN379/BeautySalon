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
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="ua"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>

    <fmt:setBundle basename="localization" var="bundle"/>
    <title>Order</title>
</head>
<body>
    <style>
        #order *{
            margin-top: 25px;
        }
    </style>
    <navbar:navbar/>
    <div class="container">
        <form action="${pageContext.request.contextPath}/order" method="post" id="order">
            <select onchange="selectChange(this);" name="service-id" form="order" required>
                <option selected disabled><fmt:message key="selectService" bundle="${bundle}"/></option>
                <c:forEach items="${services}" var="service">
                    <option value="${service.id}"><c:out value="${service.name}" /></option>
                </c:forEach>
            </select>

            <div id="masters"></div>
            <input type="date" name="calendar" id="calendar" onchange="getDate(this);" required>
            <div id="time"></div>

            <input type="submit" value="<fmt:message key="submitOrder" bundle="${bundle}"/>">
        </form>
    </div>



    <script defer>
        document.getElementById('calendar').valueAsDate = new Date();
        function getDate(date) {
            console.log(date);
            var master = document.querySelector("#masters select[name=master-id]");
            console.log(master);
            var dateValue = date.value;
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("time").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "/salon/order/time?date="+dateValue+"&id="+master.value , true);
            xhttp.send();
        }
        function selectChange(select) {
            var idService = select.value;
            getMasters(idService);
        }
        function getMasters(id) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("masters").innerHTML = this.responseText;
                }
            };
            xhttp.open("GET", "/salon/order/masters?id="+id , true);
            xhttp.send();
        }
    </script>
</body>
</html>
