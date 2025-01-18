<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title>Main page</title>
    <style>
        <%@include file="/WEB-INF/css/card.css"%>
        <%@include file="/WEB-INF/css/inputForms.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal/modalFilter.jsp"/>
<div class="container-fluid mt-4">
    <section>
        <div class="row">
            <div class="col-lg-1 col-mg-0"></div>
            <div class="col-lg-10 col-mg-12">
                <p class="mt-2 mb-2">${requestScope.error}</p>
                <div class="viewProductSearching mb-4">
                    <form action="${pageContext.request.contextPath}/product/search" method="get" class="w-100  mr-3">
                        <div class="input-group">
                            <input value="${requestScope.search}" type="text" class="form-control" id="search"
                                   placeholder=" <fmt:message key="placeholder.product.find" bundle="${lang}"/>"
                                   name="search"/>
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="submit">
                                    <fmt:message key="button.find" bundle="${lang}"/>
                                </button>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="btn btn-outline-secondary"
                            data-toggle="modal" data-target="#modal">
                        <fmt:message key="button.filter" bundle="${lang}"/>
                    </button>
                </div>
                <c:choose>
                    <c:when test="${requestScope.products.isEmpty()}">
                        <h3><fmt:message key="products.not_found" bundle="${lang}"/></h3>
                    </c:when>
                    <c:otherwise>
                        <div class="row">
                            <c:forEach var="product" items="${requestScope.products}">
                                <div class="col-lg-3 col-md-6">
                                    <div class="card mt-2 mb-2" style="width: 17.5rem;height: 26.5rem">
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
                                            <strong>${product.getCoast()}</strong><br>
                                                ${product.getTitle()}<br>
                                                ${product.getCategory()}<br>
                                            <c:choose>
                                                <c:when test="${product.getAmount()==0}">
                                                    <fmt:message key="products.none_left" bundle="${lang}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="products.remaining_in_stock"
                                                                 bundle="${lang}"/>: ${product.getAmount()}<br>
                                                </c:otherwise>
                                            </c:choose>
                                            <form action="${pageContext.request.contextPath}/user/cart/add"
                                                  method="POST">
                                                <input type="hidden" name="id" value="${product.getId()}">
                                                <button type="submit" class="btn btn-primary w-100 mt-1 btn-block">
                                                    <fmt:message key="products.to_basket" bundle="${lang}"/>
                                                </button>
                                            </form>
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
</body>
<script>
    const searchFilter = document.getElementById("search-filter")
    const search = document.getElementById("search")
    if (search && search.value.trim() !== "") {
        searchFilter.value = search.value
    }
</script>
</html>
