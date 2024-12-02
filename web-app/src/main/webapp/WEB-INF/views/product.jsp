<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product ${requestScope.product.getTitle()}</title>
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
                                         src="${pageContext.request.contextPath}/image?id=${image.getId()}"
                                         alt="Slide ${status.index + 1}">
                                </div>
                            </c:forEach>
                        </div>
                        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button"
                           data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button"
                           data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>Изображения для данного продукта отсутствуют.</p>
                </c:otherwise>
            </c:choose>
            <h4>${requestScope.product.getCoast()}</h4>
            <h5>${requestScope.product.getTitle()}</h5>
            <p class="card-text">Категория: ${requestScope.product.getCategory()}<br></p>
            <p class="text-justify">${requestScope.product.getDescription()}</p>
            <p class="card-text">Осталось: ${requestScope.product.getAmount()}</p>
        </div>
        <div class="col-lg-1 col-mg-0"></div>
    </div>
</div>
</body>
</html>
