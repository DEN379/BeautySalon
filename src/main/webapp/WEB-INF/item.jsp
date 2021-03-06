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
    <title>Record</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <%--    <script defer src="script.js"></script>--%>
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
    <div class="record-table">

        <p class="record-id"><fmt:message key="id" bundle="${bundle}"/>:    <c:out value="${record.id}" /></p>
        <p><fmt:message key="user" bundle="${bundle}"/>:   <c:out value="${record.user.firstName}" />
            <c:out value="${record.user.lastName}" /></p>
        <p><fmt:message key="master" bundle="${bundle}"/>:      <c:out value="${record.userMaster.firstName}" />
            <c:out value="${record.userMaster.lastName}" /></p>
        <p><fmt:message key="service" bundle="${bundle}"/>:     <c:out value="${record.service.name}" /></p>
        <p class="status-id"><fmt:message key="status" bundle="${bundle}"/>:    <c:out value="${record.status.value()}" /></p>

        <form action="${pageContext.request.contextPath}/admin/records/updateTime?id=${record.id}" method="post" id="time-form">
            <select name="time" onchange="setButton();" required>
                <option selected onload="load(this);" id="first-option" disabled>???</option>
                <c:forEach items="${recTime}" var="time">
                    <option value="<c:out value="${time}" />"><c:out value="${time}" />:00</option>
                </c:forEach>
            </select>
            <input type="submit" value="<fmt:message key="changeTime" bundle="${bundle}"/>" id="time-button" style="display: none">
        </form>

        <div id="time-accept"></div>

        <c:if test="${record.status_id == 2}">
            <form action="${pageContext.request.contextPath}/admin/records/accept?id=${record.id}" method="post">
                <input type="submit" value="<fmt:message key="acceptPayment" bundle="${bundle}"/>">
            </form>
        </c:if>

        <c:if test="${record.status_id < 4}">
            <form action="${pageContext.request.contextPath}/admin/records/cancel?id=${record.id}" method="post">
                <input type="submit" onclick="alert('Do yo want to cancel a record?')"
                       value="<fmt:message key="cancelRecord" bundle="${bundle}"/>">
            </form>
        </c:if>

        <hr>
    </div>
</div>
    <script defer>
        document.addEventListener("DOMContentLoaded", load);
        function load() {
            var f = document.querySelector("#first-option");
            console.log(f);
            var test = "${record.time}";
            console.log(test);
            var str = "<c:out value="${record.time}" />";
            console.log(str);
            f.value = str.substring(11,13);
            f.innerHTML = "Current - "+str.substring(11,13)+":00";
        }
        function setButton() {
            var form = document.querySelector("#time-button");
            console.log(form);
            form.style.display = "block";
        }
    </script>
</body>
</html>
