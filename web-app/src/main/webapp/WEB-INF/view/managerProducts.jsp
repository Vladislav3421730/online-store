<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="products.title" bundle="${lang}" /></title>
    <style>
        <%@include file="/WEB-INF/css/inputForms.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:include page="modal/modalCoastFilter.jsp"/>
<div class="container mt-4">
    <div class="viewProductSearching mb-2">
        <form action="${pageContext.request.contextPath}/products/manager/search" method="get" class="w-100  mr-3">
            <div class="input-group">
                <input value="${requestScope.search}" type="text" class="form-control" id="search" placeholder="<fmt:message key='products.searchPlaceholder' bundle='${lang}' />"
                       name="id"/>
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit"><fmt:message key="products.searchButton" bundle="${lang}" /></button>
                </div>
            </div>
        </form>
        <div>
            <button type="button" class="btn btn-outline-secondary" style="margin-bottom: 17px" data-toggle="modal"
                    data-target="#modal">
                <fmt:message key="products.applyFilters" bundle="${lang}" />
            </button>
        </div>
    </div>
    <p style="color: red">${requestScope.error}</p>
    <input type="button" class="btn btn-success mb-3" value="<fmt:message key='products.addButton' bundle='${lang}' />"
           onclick="window.location.href='${pageContext.request.contextPath}/manager/product/add'">
    <c:choose>
        <c:when test="${requestScope.products.isEmpty()}">
            <h3><fmt:message key="products.noProductsFound" bundle="${lang}" /></h3>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="products.table.number" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.id" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.title" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.amount" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.coast" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.info" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.edit" bundle="${lang}" /></th>
                    <th scope="col"><fmt:message key="products.table.delete" bundle="${lang}" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${requestScope.products}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${product.getId()}</td>
                        <td>${product.getTitle()}</td>
                        <td>${product.getAmount()}</td>
                        <td>${product.getCoast()}</td>
                        <td>
                            <input type="button" class="btn btn-primary" value="<fmt:message key='products.table.infoButton' bundle='${lang}' />"
                                   onclick="window.location.href='${pageContext.request.contextPath}/products/${product.getId()}'">
                        </td>
                        <td>
                            <input type="button" class="btn btn-success" value="<fmt:message key='products.table.editButton' bundle='${lang}' />"
                                   onclick="window.location.href='${pageContext.request.contextPath}/manager/product/edit?id=${product.getId()}'">
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/products/delete/${product.getId()}" method="post">
                                <input type="submit" value="<fmt:message key='products.table.deleteButton' bundle='${lang}' />" class="btn btn-danger">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/products/manager?page=${currentPage - 1}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}">&laquo;
                        <fmt:message key="products.pagination.prev" bundle="${lang}" /></a>
                </c:if>
                <c:if test="${totalPages > 1}">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="${pageContext.request.contextPath}/products/manager?page=${i}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}"
                           class="${currentPage == i ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </c:if>
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/products/manager?page=${currentPage + 1}&minPrice=${filter.getMinPrice()}&maxPrice=${filter.getMaxPrice()}">
                        <fmt:message key="products.pagination.next" bundle="${lang}" /> &raquo;</a>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
