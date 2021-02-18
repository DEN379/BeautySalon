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
    <title>My Orders</title>
</head>
<body>
<c:forEach items="${records}" var="record">
    <div class="record-table">
        <p><c:out value="${record.service.name}" /></p>
        <p><c:out value="${record.time}" /></p>
        <p><c:out value="${record.userMaster.firstName}" /></p>
        <p><c:out value="${record.userMaster.lastName}" /></p>
        <p>Price: <c:out value="${record.serviceMaster.price}" /></p>
        <p class="status-id"><c:out value="${record.status.value()}" /></p>
        <c:if test="${record.status_id == 1}">
        <form action="${pageContext.request.contextPath}/myOrders/paid?id=${record.id}" method="post">
            <input type="submit" value="Pay">
        </form>
        </c:if>
        <c:if test="${record.status_id == 4}">
<%--            <form action="${pageContext.request.contextPath}/order/comment?id=${record.id}" method="get">--%>
<%--                <input type="submit" value="Leave a comment">--%>
<%--            </form>--%>
            <a href="${pageContext.request.contextPath}/order/comment?id=${record.id}">Leave a comment</a>
        </c:if>

        <hr>
    </div>
</c:forEach>
</body>
</html>
