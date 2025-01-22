<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="orders.title" bundle="${lang}" /></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4">
    <c:choose>
        <c:when test="${requestScope.orders.isEmpty()}">
            <h3><fmt:message key="orders.noOrders" bundle="${lang}" /></h3>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/manager/order/search" method="get" class="w-100 mr-3">
                <div class="input-group mb-3">
                    <input value="${requestScope.search}" type="text" class="form-control" id="search"
                           placeholder="<fmt:message key='orders.searchPlaceholder' bundle='${lang}' />"
                           name="search"/>
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="submit">
                            <fmt:message key="orders.searchButton" bundle="${lang}" />
                        </button>
                    </div>
                </div>
            </form>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="orders.table.number" bundle="${lang}" /></th>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="orders.table.email" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="orders.table.created" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="orders.table.status" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="orders.table.info" bundle="${lang}" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${requestScope.orders}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${order.getId()}</td>
                        <td>${order.getEmail()}</td>
                        <td><p><fmt:formatDate value="${order.getCreatedAtAsDate()}"
                                               pattern="yyyy-MM-dd HH:mm"/></p></td>
                        <th>${order.getStatus()}</th>
                        <td>
                            <input type="button" class="btn btn-primary" value="<fmt:message key='orders.table.infoButton' bundle='${lang}' />"
                                   onclick="window.location.href='${pageContext.request.contextPath}/orders/${order.getId()}'">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
