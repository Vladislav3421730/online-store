<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="account.title" bundle="${lang}" /> ${user.username}</title>
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
            <h2><fmt:message key="account.email" bundle="${lang}" />: ${user.email}</h2>
            <h3><fmt:message key="account.username" bundle="${lang}" />: ${user.username}</h3>
            <h3><fmt:message key="account.phone" bundle="${lang}" />: ${user.phoneNumber}</h3>
            <input type="button" class="btn btn-danger mt-1 mb-2" value="<fmt:message key='account.logout' bundle='${lang}' />"
                   onclick="window.location.href='${pageContext.request.contextPath}/logout'">
            <c:choose>
                <c:when test="${empty user.orders}">
                    <h4><fmt:message key="account.noOrders" bundle="${lang}" /></h4>
                </c:when>
                <c:otherwise>
                    <h3><fmt:message key="account.orderHistory" bundle="${lang}" /></h3>
                    <c:forEach var="order" items="${user.orders}">
                        <div class="mt-4">
                            <hr>
                            <div class="orderInfo">
                                <strong><fmt:message key="account.orderNumber" bundle="${lang}" /> ${order.id}</strong>
                                <div class="${order.status == 'доставлен' ? 'delivered mx-2' : 'status mx-2'}">
                                        ${order.status}
                                </div>
                            </div>
                            <strong><fmt:message key="account.totalPrice" bundle="${lang}" /> ${order.totalPrice}</strong><br>
                            <p><fmt:message key="account.orderTime" bundle="${lang}" />: <fmt:formatDate value="${order.createdAtAsDate}"
                                                                                                         pattern="yyyy-MM-dd HH:mm"/></p>
                            <p><fmt:message key="account.deliveryAddress" bundle="${lang}" />: ${order.address.region}, ${order.address.town}, ${order.address.exactAddress}</p>
                            <div class="row">
                                <c:forEach var="orderItem" items="${order.orderItems}">
                                    <div class="col-lg-3 col-md-6">
                                        <div class="card mt-2 mb-2" style="width: 17.5rem;height: 24rem">
                                            <a href="${pageContext.request.contextPath}/products/${orderItem.product.id}">
                                                <c:choose>
                                                    <c:when test="${empty orderItem.product.imageList}">
                                                        <img src="https://brilliant24.ru/files/cat/template_01.png"
                                                             class="card-img-top">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/image?id=${orderItem.product.imageList[0].id}"
                                                             class="card-img-top"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </a>
                                            <div class="card-body">
                                                <strong>${orderItem.product.coast}</strong><br>
                                                    ${orderItem.product.title}<br>
                                                    ${orderItem.product.category}<br>
                                                <fmt:message key="account.quantity" bundle="${lang}" /> ${orderItem.amount}
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
