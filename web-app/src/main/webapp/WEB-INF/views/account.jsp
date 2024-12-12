<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>account ${sessionScope.user.getUsername()}</title>
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
            <h2>Электронная почта: ${sessionScope.user.getEmail()}</h2>
            <h3>Логин: ${sessionScope.user.getUsername()}</h3>
            <h3>Номер телефона: ${sessionScope.user.getPhoneNumber()}</h3>
            <input type="button" class="btn btn-danger mt-1 mb-2" value="Выйти из аккаунта"
                   onclick="window.location.href='${pageContext.request.contextPath}/logout'">
            <c:choose>
                <c:when test="${sessionScope.user.getOrders().isEmpty()}">
                    <h4>Ваш список заказов на данный момент пуст</h4>
                </c:when>
                <c:otherwise>
                    <h3>История ваших заказов</h3>
                    <c:forEach var="order" items="${sessionScope.user.getOrders()}">
                        <div class="mt-4">
                            <hr>
                            <div class="orderInfo">
                                <strong>Номер заказа ${order.getId()}</strong>
                                <div class="${order.getStatus().getDisplayName() == 'доставлен' ? 'delivered mx-2' : 'status mx-2'}">
                                        ${order.getStatus().getDisplayName()}
                                </div>
                            </div>
                            <strong>Общая сумма заказа ${order.getTotalPrice()}</strong><br>
                            <p>Время заказа: <fmt:formatDate value="${order.getCreatedAtAsDate()}"
                                                             pattern="yyyy-MM-dd HH:mm"/></p>
                            <p>Адресс
                                доставки: ${order.getAddress().getRegion()}, ${order.getAddress().getTown()}, ${order.getAddress().getExactAddress()}</p>
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
                                                Количество ${orderItem.getAmount()}
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
