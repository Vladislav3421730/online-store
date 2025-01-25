<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="cart.title" bundle="${lang}"/></title>
    <style>
        <%@include file="/WEB-INF/css/card.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal/modalOrder.jsp"/>
<div class="container-fluid mt-4">
    <section>
        <div class="row">
            <div class="col-lg-1 col-mg-0"></div>
            <div class="col-lg-10 col-mg-12">
                <a class="btn btn-success mb-1 mt-1" href="${pageContext.request.contextPath}/products"><fmt:message
                        key="cart.home" bundle="${lang}"/></a>
                <c:choose>
                    <c:when test="${requestScope.error!=null}">
                        <h4 style="color: red"><fmt:message key="${requestScope.error}" bundle="${lang}"/></h4>
                    </c:when>
                    <c:when test="${requestScope.success!=null}">
                        <h4 style="color: green"><fmt:message key="${requestScope.success}" bundle="${lang}"/></h4>
                    </c:when>
                </c:choose>


                <c:choose>
                    <c:when test="${user.getCarts().isEmpty()}">
                        <h3><fmt:message key="cart.empty" bundle="${lang}"/></h3>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach var="cart" items="${user.getCarts()}" varStatus="status">
                                <div class="col-lg-3 col-md-6">
                                    <div class="card mt-2 mb-2" style="width: 17.5rem;height: 29rem">
                                        <a href="${pageContext.request.contextPath}/products/${cart.getProduct().getId()}">
                                            <c:choose>
                                                <c:when test="${cart.getProduct().getImageList().isEmpty()}">
                                                    <img src="https://brilliant24.ru/files/cat/template_01.png"
                                                         class="card-img-top">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/images/${cart.getProduct().getImageList().get(0).getId()}"
                                                         class="card-img-top"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                        <div class="card-body">
                                            <strong>${cart.getProduct().getCoast()}</strong><br>
                                                ${cart.getProduct().getTitle()}<br>
                                            <div style="display: flex">
                                                <fmt:message key="cart.quantity" bundle="${lang}"/>:
                                                <form action="${pageContext.request.contextPath}/user/cart/decrement/${status.index}"
                                                      method="post">
                                                    <button type="submit" id="minus-button">-</button>
                                                </form>
                                                    ${cart.getAmount()}
                                                <form action="${pageContext.request.contextPath}/user/cart/increment/${status.index}"
                                                      method="post">
                                                    <input type="hidden" name="totalCoast"
                                                           value="${requestScope.totalCoast}">
                                                    <button type="submit" id="plus-button">+</button>
                                                </form>
                                            </div>
                                            <p><fmt:message key="cart.remaining"
                                                            bundle="${lang}"/>: ${cart.getProduct().getAmount()}</p>
                                            <form action="${pageContext.request.contextPath}/user/cart/delete/${status.index}"
                                                  method="post">
                                                <input type="submit" class="btn btn-danger"
                                                       value="<fmt:message key='cart.remove' bundle='${lang}'/>"/>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="mt-2 mb-2 d-flex" style="justify-content: flex-end">
                            <button type="button" class="btn btn-primary mx-3" data-toggle="modal" data-target="#modal">
                                <fmt:message key="cart.checkout" bundle="${lang}"/> ${requestScope.totalCoast}
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
