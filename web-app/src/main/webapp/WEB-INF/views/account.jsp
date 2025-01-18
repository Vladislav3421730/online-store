<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="account.title" bundle="${lang}" /> ${sessionScope.user.getUsername()}</title>
    <style>
        <%@include file="/WEB-INF/css/card.css"%>
        <%@include file="/WEB-INF/css/status.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid mt-2">
    <div class="row">
        <div class="col-lg-1 col-mg-0"></div>
        <div class="col-lg-10 col-mg-12">
            <h2><fmt:message key="account.email" bundle="${lang}" />: ${sessionScope.user.getEmail()}</h2>
            <h3><fmt:message key="account.username" bundle="${lang}" />: ${sessionScope.user.getUsername()}</h3>
            <h3><fmt:message key="account.phone" bundle="${lang}" />: ${sessionScope.user.getPhoneNumber()}</h3>
            <input type="button" class="btn btn-danger mt-1 mb-2" value="<fmt:message key='account.logout' bundle='${lang}' />"
                   onclick="window.location.href='${pageContext.request.contextPath}/logout'">
            <c:choose>
                <c:when test="${sessionScope.user.getOrders().isEmpty()}">
                    <h4><fmt:message key="account.noOrders" bundle="${lang}" /></h4>
                </c:when>
                <c:otherwise>
                    <h3><fmt:message key="account.orderHistory" bundle="${lang}" /></h3>
                    <c:forEach var="order" items="${sessionScope.user.getOrders()}">
                        <div class="mt-4">
                            <hr>
                            <div class="orderInfo">
                                <strong><fmt:message key="account.orderNumber" bundle="${lang}" /> ${order.getId()}</strong>
                                <div class="${order.getStatus() == 'доставлен' ? 'delivered mx-2' : 'status mx-2'}">
                                        ${order.getStatus()}
                                </div>
                            </div>
                            <strong><fmt:message key="account.totalPrice" bundle="${lang}" /> ${order.getTotalPrice()}</strong><br>
                            <p><fmt:message key="account.orderTime" bundle="${lang}" />: <fmt:formatDate value="${order.getCreatedAtAsDate()}"
                                                                                                         pattern="yyyy-MM-dd HH:mm"/></p>
                            <p><fmt:message key="account.deliveryAddress" bundle="${lang}" />: ${order.getAddress().getRegion()}, ${order.getAddress().getTown()}, ${order.getAddress().getExactAddress()}</p>
                            <div class="row">
                                <c:forEach var="orderItem" items="${order.getOrderItems()}">
                                    <div class="col-lg-3 col-md-6">
                                        <div class="card mt-2 mb-2" style="width: 17.5rem;height: 24rem">
                                            <a href="${pageContext.request.contextPath}/product/get?id=${orderItem.getProduct().getId()}">
                                                <c:choose>
                                                    <c:when test="${orderItem.getProduct().getImageList().isEmpty()}">
                                                        <img src="https://brilliant24.ru/files/cat/template_01.png"
                                                             class="card-img-top">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/image?id=${orderItem.getProduct().getImageList().get(0).getId()}"
                                                             class="card-img-top"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                            <div class="card-body">
                                                <strong>${orderItem.getProduct().getCoast()}</strong><br>
                                                    ${orderItem.getProduct().getTitle()}<br>
                                                    ${orderItem.getProduct().getCategory()}<br>
                                                <fmt:message key="account.quantity" bundle="${lang}" /> ${orderItem.getAmount()}
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-lg-1 col-mg-0"></div>
    </div>
</div>
</body>
</html>
