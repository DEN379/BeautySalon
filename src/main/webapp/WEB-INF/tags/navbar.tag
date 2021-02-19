<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Page navigation bar" pageEncoding="UTF-8"%>
<%@attribute name="navbar" fragment="true" %>

<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
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
        <a href="${pageContext.request.contextPath}/admin/records">Orders</a>
        <a href="${pageContext.request.contextPath}/admin/createService">Create service</a>
        <a href="${pageContext.request.contextPath}/admin/addMaster">Add master</a>
        <a href="${pageContext.request.contextPath}/admin/users">All users</a>
    </c:if>
    <c:if test="${sessionScope.role == 'Master'}">
        <a href="${pageContext.request.contextPath}/master/timeTable">Time table</a>
    </c:if>
    <c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true && sessionScope.role == 'Client'}">
        <a href="${pageContext.request.contextPath}/order">Order</a>
        <a href="${pageContext.request.contextPath}/myOrders">My orders</a>
    </c:if>
</nav>