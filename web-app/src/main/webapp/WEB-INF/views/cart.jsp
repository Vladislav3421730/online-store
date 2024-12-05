<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Your cart</title>
    <style>
        <%@include file="/WEB-INF/css/card.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modalOrder.jsp"/>
<div class="container-fluid mt-4">
    <section>
        <div class="row">
            <div class="col-lg-1 col-mg-0"></div>
            <div class="col-lg-10 col-mg-12">
                <a class="btn btn-success mb-1 mt-1" href="${pageContext.request.contextPath}/">На главную</a>
                <p style="color: red">${requestScope.error}</p>
                <p style="color: green">${requestScope.success}</p>
                <c:choose>
                    <c:when test="${sessionScope.user.getCarts().isEmpty()}">
                        <h3>Ваша корзина пуста</h3>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach var="cart" items="${sessionScope.user.getCarts()}" varStatus="status">
                                <div class="col-lg-3 col-md-6">
                                    <div class="card mt-2 mb-2" style="width: 17.5rem;height: 29rem">
                                        <a href="${pageContext.request.contextPath}/product/get?id=${cart.getProduct().getId()}">
                                            <c:choose>
                                                <c:when test="${cart.getProduct().getImageList().isEmpty()}">
                                                    <img src="https://brilliant24.ru/files/cat/template_01.png"
                                                         class="card-img-top">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/image?id=${cart.getProduct().getImageList().get(0).getId()}"
                                                         class="card-img-top"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                        <div class="card-body">
                                            <strong>${cart.getProduct().getCoast()}</strong><br>
                                                ${cart.getProduct().getTitle()}<br>
                                            <div style="display: flex">
                                                Количество:
                                                <form action="${pageContext.request.contextPath}/user/cart/decrement?index=${status.index}"
                                                      method="post">
                                                    <button type="submit" id="minus-button">-</button>
                                                </form>
                                                    ${cart.getAmount()}
                                                <form action="${pageContext.request.contextPath}/user/cart/increment?index=${status.index}"
                                                      method="post">
                                                    <button type="submit" id="plus-button">+</button>
                                                </form>
                                            </div>
                                            <p>Осталось: ${cart.getProduct().getAmount()}</p>
                                            <form action="${pageContext.request.contextPath}/user/cart/delete?index=${status.index}"
                                                  method="post">
                                                <input type="submit" class="btn btn-danger" value="убрать из корзины"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="mt-2 mb-2 d-flex" style="justify-content: flex-end">
                            <button type="button" class="btn btn-primary mx-3" data-toggle="modal" data-target="#modal">
                                Оформить заказ ${requestScope.totalCoast}
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="col-lg-1 col-mg-0"></div>
        </div>
    </section>
</body>
</html>
