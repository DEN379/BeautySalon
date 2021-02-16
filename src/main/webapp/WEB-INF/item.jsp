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
    <title>Record</title>
    <%--    <script defer src="script.js"></script>--%>
</head>
<body>

    <div class="record-table">
        <p class="record-id"><c:out value="${record.id}" /></p>
        <p><c:out value="${record.user.firstName}" /></p>
        <p><c:out value="${record.user.lastName}" /></p>
        <p><c:out value="${record.userMaster.firstName}" /></p>
        <p><c:out value="${record.userMaster.lastName}" /></p>
        <p><c:out value="${record.service.name}" /></p>
        <p class="status-id"><c:out value="${record.status_id}" /></p>
<%--        <p><c:out value="${record.time}" /></p>--%>
        <form action="${pageContext.request.contextPath}/records/updateTime?id=${record.id}" method="post" id="time-form">
            <select name="time" onchange="setButton();" required>
                <option selected onload="load(this);" id="first-option" disabled>???</option>
                <c:forEach items="${recTime}" var="time">
                    <option value="<c:out value="${time}" />"><c:out value="${time}" />:00</option>
                </c:forEach>
            </select>
            <input type="submit" value="Change time" id="time-button" style="display: none">
        </form>

        <div id="time-accept">


        </div>
        <c:if test="${record.status_id == 2}">
            <form action="${pageContext.request.contextPath}/records/accept?id=${record.id}" method="post">
                <input type="submit" value="Accept payment">
            </form>
<%--            <a href="${pageContext.request.contextPath}/records/accept?id=${record.id}"--%>
<%--               class="accept-button" >Accept payment</a>--%>

        </c:if>
        <form action="${pageContext.request.contextPath}/records/cancel?id=${record.id}" method="post">
            <input type="submit" onclick="alert('Do yo want to cancel a record?')" value="Cancel record">
        </form>
<%--        <a href="${pageContext.request.contextPath}/records/cancel?id=${record.id}"--%>
<%--           onclick="alert('Do yo want to cancel a record?')">Cancel record</a>--%>
        <hr>
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
            // var button = document.querySelector("#time-accept");
            // button.innerHTML = "";
            var form = document.querySelector("#time-button");
            console.log(form);
            form.style.display = "block";
        }
    </script>
</body>
</html>
