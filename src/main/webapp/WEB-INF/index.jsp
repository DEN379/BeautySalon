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
    <title>Beauty Salon</title>
</head>
<body>
<c:if test="${sessionScope.authenticated == null}">
    <a href="${pageContext.request.contextPath}/login">Login</a>
    <a href="${pageContext.request.contextPath}/register">Register</a>
</c:if>
<c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true}">
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout">
    </form>
</c:if>
<c:if test="${sessionScope.role == 'Admin'}">
    <a href="${pageContext.request.contextPath}/records">Orders</a>
</c:if>
<c:if test="${sessionScope.role == 'Master'}">
    <a href="${pageContext.request.contextPath}/records">Records</a>
    <a href="${pageContext.request.contextPath}/timeTable">Time table</a>
</c:if>
<c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true && sessionScope.role == 'Client'}">
    <a href="${pageContext.request.contextPath}/order">Order</a>
</c:if>
<hr>

<select>
    <option disabled selected>Select master for service</option>
    <c:forEach items="${masters}" var="master">
        <option><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></option>
    </c:forEach>
</select>

<c:forEach items="${services}" var="service">
    <div>
        <p><c:out value="${service.name}" /></p>
        <p><c:out value="${service.description}" /></p>
    </div>
</c:forEach>


<hr>

<select>
    <option>Select service to choose master</option>
    <c:forEach items="${services}" var="service">
        <option><c:out value="${service.name}" /></option>
    </c:forEach>
</select>
<c:forEach items="${masters}" var="master">
    <div>
        <p><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></p>
        <p><c:out value="${master.mark}" /></p>
    </div>
</c:forEach>

<hr>

</body>
</html>