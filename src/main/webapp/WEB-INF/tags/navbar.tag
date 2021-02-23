<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Page navigation bar" pageEncoding="UTF-8"%>
<%@attribute name="navbar" fragment="true" %>

<c:if test="${sessionScope.locale == null}">
    <fmt:setLocale value="ua"/>
</c:if>
<c:if test="${sessionScope.locale != null}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="localization" var="bundle"/>

<style>
    .navbar{
        margin: 0;
        background-color: peachpuff;
    }
    .wrapper {
        margin-top: 20px;
        margin-bottom: 20px;
        display: grid;
        grid-template-columns: 1fr 1fr;
    }
    .auth{
        display: flex;
        justify-content: flex-end;
        align-content: space-around;
    }
    .wrapper div{
        margin-left: 40px;
        display: flex;
        justify-items: flex-end;
    }
    .auth a{
        margin-left: 20px;
    }
    .admin-buttons a, .client-buttons a{
        margin-left: 20px;
    }
    .logo{
        width: 29%;
        margin-left: 17px;
    }
</style>

<div class="navbar">

    <div class="container">

    <div class="wrapper">

    <div style="display: flex; align-items: center;">
        <a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/sources/logo.jpg" alt="logo" class="logo"></a>
        <c:if test="${sessionScope.role == 'Admin'}">
            <div class="admin-buttons">
                <a href="${pageContext.request.contextPath}/admin/records" class="btn btn-primary">
                    <fmt:message key="orders" bundle="${bundle}"/>
                </a>
                <a href="${pageContext.request.contextPath}/admin/createService" class="btn btn-primary">
                    <fmt:message key="createService" bundle="${bundle}"/>
                </a>
                    <%--        <a href="${pageContext.request.contextPath}/admin/addMaster"><fmt:message key="addMaster" bundle="${bundle}"/></a>--%>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">
                    <fmt:message key="allUsers" bundle="${bundle}"/>
                </a>
            </div>
        </c:if>
        <c:if test="${sessionScope.role == 'Master'}">
            <div>
                <a href="${pageContext.request.contextPath}/master/timeTable" class="btn btn-primary">
                    <fmt:message key="timeTable" bundle="${bundle}"/></a>
            </div>

        </c:if>
        <c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true && sessionScope.role == 'Client'}">
            <div class="client-buttons">
                <a href="${pageContext.request.contextPath}/order" class="btn btn-primary">
                    <fmt:message key="order" bundle="${bundle}"/></a>
                <a href="${pageContext.request.contextPath}/myOrders" class="btn btn-primary">
                    <fmt:message key="myOrders" bundle="${bundle}"/></a>
            </div>
        </c:if>
    </div>

    <div class="wrapper">



        <div>
            <c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true}">
                <span><fmt:message key="loggedInAs" bundle="${bundle}"/> <b>${sessionScope.email}, ${sessionScope.role}</b></span>
            </c:if>
        </div>

        <c:if test="${sessionScope.authenticated != null && sessionScope.authenticated == true}">
        <div>

                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <input type="submit" value="<fmt:message key="logout" bundle="${bundle}"/>" class="btn btn-outline-danger">
                </form>

        </div>
        </c:if>



        <c:if test="${sessionScope.authenticated == null}">
            <div class="auth">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-primary">
                    <fmt:message key="login" bundle="${bundle}"/>
                </a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-primary">
                    <fmt:message key="register" bundle="${bundle}"/>
                </a>
            </div>
        </c:if>

        <div></div>

        <div>
            <form action="${pageContext.request.contextPath}" method="get">
                <select name="locale" id="local">
                    <option value="ua">UA</option>
                    <option value="en">EN</option>
                </select>
                <input type="submit" value="Change" class="btn btn-outline-secondary" style="padding: 3px;">
            </form>
        </div>
    </div>
    </div>
    </div>
</div>

<%--<script defer>  onchange="local(this);" --%>
<%--    function local(opt) {--%>
<%--        var loc = opt.value;--%>
<%--        var xhttp = new XMLHttpRequest();--%>
<%--        xhttp.onreadystatechange = function() {--%>
<%--            if (this.readyState == 4 && this.status == 200) {--%>
<%--                //document.getElementById("masters").innerHTML = this.responseText;--%>
<%--                console.log(opt);--%>
<%--            }--%>
<%--        };--%>
<%--        xhttp.open("GET", "/salon?locale="+loc, true);--%>
<%--        xhttp.send();--%>
<%--    }--%>

<%--</script>--%>