<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="order.title" bundle="${lang}" /></title>
    <style>
        <%@include file="/WEB-INF/css/card.css"%>
        <%@include file="/WEB-INF/css/status.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal/modalStatus.jsp"/>
<div class="container">
    <div class="mt-4">
        <div class="d-flex">
            <button type="button" class="btn btn-success mb-1 mt-1"
                    data-toggle="modal" data-target="#modal">
                <fmt:message key="order.changeStatus" bundle="${lang}" />
            </button>
            <input type="button" class="btn btn-primary mx-3" value="<fmt:message key='order.ordersTable' bundle='${lang}' />"
                   onclick="window.location.href='${pageContext.request.contextPath}/manager/orders'">
        </div>
        <hr>
        <br>
        <strong>email: ${requestScope.user.getEmail()}</strong><br>
        <strong><fmt:message key="order.username" bundle="${lang}" />: ${requestScope.user.getUsername()}</strong><br>
        <strong><fmt:message key="order.phone" bundle="${lang}" />: ${requestScope.user.getPhoneNumber()}</strong><br>
        <div class="orderInfo">
            <strong><fmt:message key="order.orderNumber" bundle="${lang}" /> ${requestScope.orderDto.getId()}</strong>
            <div class="${requestScope.orderDto.getStatus() == 'доставлен' ? 'delivered mx-2' : 'status mx-2'}">
                ${requestScope.orderDto.getStatus()}
            </div>
        </div>
        <strong><fmt:message key="order.totalPrice" bundle="${lang}" /> ${requestScope.orderDto.getTotalPrice()}</strong><br>
        <p><fmt:message key="order.orderTime" bundle="${lang}" />: <fmt:formatDate value="${requestScope.orderDto.getCreatedAtAsDate()}"
                                                                                   pattern="yyyy-MM-dd HH:mm"/></p>
        <p><fmt:message key="order.deliveryAddress" bundle="${lang}" />: ${requestScope.orderDto.getAddress().getRegion()}, ${requestScope.orderDto.getAddress().getTown()},
            ${requestScope.orderDto.getAddress().getExactAddress()}</p>
        <div class="row">
            <c:forEach var="orderItem" items="${requestScope.orderDto.getOrderItems()}">
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
                            <fmt:message key="order.quantity" bundle="${lang}" /> ${orderItem.getAmount()}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/viewStatusInSelect.js"%>
</script>
</html>
