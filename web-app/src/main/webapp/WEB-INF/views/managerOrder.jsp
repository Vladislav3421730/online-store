<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Order ${requestScope.order.getId()} </title>
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
                Изменить стуатус
            </button>
            <input type="button" class="btn btn-primary mx-3" value="Таблица с заказами"
                   onclick="window.location.href='${pageContext.request.contextPath}/manager/orders'">
        </div>
        <hr>
        <br>
        <strong>email: ${requestScope.userDto.getEmail()}</strong><br>
        <strong>Имя: ${requestScope.userDto.getUsername()}</strong><br>
        <strong>Телефон: ${requestScope.userDto.getPhoneNumber()}</strong><br>
        <div class="orderInfo">
            <strong>Номер заказа ${requestScope.orderDto.getId()}</strong>
            <div class="${requestScope.orderDto.getStatus() == 'доставлен' ? 'delivered mx-2' : 'status mx-2'}">
                ${requestScope.orderDto.getStatus()}
            </div>
        </div>
        <strong>Общая сумма заказа ${requestScope.orderDto.getTotalPrice()}</strong><br>
        <p>Время заказа: <fmt:formatDate value="${requestScope.orderDto.getCreatedAtAsDate()}"
                                         pattern="yyyy-MM-dd HH:mm"/></p>
        <p>Адресс доставки: ${requestScope.orderDto.getAddress().getRegion()}, ${requestScope.orderDto.getAddress().getTown()},
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
                            Количество ${orderItem.getAmount()}
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
