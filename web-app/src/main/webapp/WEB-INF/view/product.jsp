<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="product.title" bundle="${lang}" /> ${requestScope.product.getTitle()}</title>
    <style>
        <%@include file="/WEB-INF/css/corousel.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-lg-1 col-mg-0"></div>
        <div class="col-lg-10 col-mg-12">
            <c:choose>
                <c:when test="${!requestScope.product.getImageList().isEmpty()}">
                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel"
                         data-interval="4000">
                        <ol class="carousel-indicators">
                            <c:forEach var="image" items="${requestScope.product.getImageList()}" varStatus="status">
                                <li data-target="#carouselExampleIndicators" data-slide-to="${status.index}"
                                    class="${status.first ? 'active' : ''}"></li>
                            </c:forEach>
                        </ol>

                        <div class="carousel-inner">
                            <c:forEach var="image" items="${requestScope.product.getImageList()}" varStatus="status">
                                <div class="carousel-item ${status.first ? 'active' : ''}">
                                    <img class="d-block w-100"
                                         src="${pageContext.request.contextPath}/images/${image.getId()}"
                                         alt="<fmt:message key='product.image.alt' bundle='${lang}' /> ${status.index + 1}">
                                </div>
                            </c:forEach>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button"
                           data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only"><fmt:message key="carousel.previous" bundle="${lang}" /></span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button"
                           data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only"><fmt:message key="carousel.next" bundle="${lang}" /></span>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <p><fmt:message key="product.noImages" bundle="${lang}" /></p>
                </c:otherwise>
            </c:choose>
            <h4>${requestScope.product.getCoast()}</h4>
            <h5>${requestScope.product.getTitle()}</h5>
            <p class="card-text"><fmt:message key="product.category" bundle="${lang}" />: ${requestScope.product.getCategory()}<br></p>
            <p class="text-justify">${requestScope.product.getDescription()}</p>
            <p class="card-text">
                <c:choose>
                    <c:when test="${requestScope.product.getAmount()==0}">
                        <fmt:message key="product.outOfStock" bundle="${lang}" />
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="product.remaining" bundle="${lang}" />: ${requestScope.product.getAmount()}
                    </c:otherwise>
                </c:choose>
            </p>
            <form action="${pageContext.request.contextPath}/user/cart/add" method="POST">
                <input type="hidden" name="id" value="${requestScope.product.getId()}">
                <button type="submit" class="btn btn-primary mt-2"><fmt:message key="product.addToCart" bundle="${lang}" /></button>
            </form>
        </div>
        <div class="col-lg-1 col-mg-0"></div>
    </div>
</div>
</body>
</html>
