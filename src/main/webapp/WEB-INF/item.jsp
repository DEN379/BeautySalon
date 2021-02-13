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
        <p><c:out value="${record.time}" /></p>
        <c:if test="${record.status_id == 1}">
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
</body>
</html>
