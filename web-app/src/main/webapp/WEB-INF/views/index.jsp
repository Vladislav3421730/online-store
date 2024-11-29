<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main page</title>
    <style>
        img {
            width: 7rem;
            height: 16rem;
        }
        .card {
            transition: all 0.3s ease-in-out;
        }
        .card:hover {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal.jsp"/>
<div class="container-fluid mt-4">
    <section>
        <div class="row">
            <div class="col-lg-1 col-mg-0"></div>
            <div class="col-lg-10 col-mg-12">
                <div class="d-flex align-items-center">
                    <form action="${pageContext.request.contextPath}/product/search" method="get" class="w-100  mr-3">
                        <div class="input-group mb-3">
                            <input value="${requestScope.search}" type="text" class="form-control" placeholder="Найти"
                                   name="search"/>
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="submit">Поиск</button>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-outline-secondary" style="margin-bottom: 17px"
                            data-toggle="modal" data-target="#modal">
                        Применить фильтры
                    </button>
                </div>
                <c:choose>
                    <c:when test="${requestScope.products.isEmpty()}">
                        <h3>Товаров не найдено</h3>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach var="product" items="${requestScope.products}">
                                <div class="col-lg-3 col-md-6">
                                    <div class="card mt-2" style="width: 17rem;height: 26rem">
                                        <a href="${pageContext.request.contextPath}/product/get?id=${product.getId()}">
                                            <c:choose>
                                                <c:when test="${product.getImageList().isEmpty()}">
                                                    <img src="https://brilliant24.ru/files/cat/template_01.png"
                                                         class="card-img-top">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/image?id=${product.getImageList().get(0).getId()}"
                                                         class="card-img-top"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                        <div class="card-body">
                                            <h5 class="card-title">${product.getCoast()}</h5>
                                            <p class="card-text">${product.getTitle()}</p>
                                            ${product.getCategory()}<br>
                                            Осталось: ${product.getAmount()}
                                        </div>

                                    </div>

                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="col-lg-1 col-mg-0"></div>
        </div>
        <button class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/product/add'">
            Добавить товар
        </button>
    </section>
</div>
</body>
</html>