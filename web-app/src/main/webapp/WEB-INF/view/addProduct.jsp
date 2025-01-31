<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="product.adding.title" bundle="${lang}"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <style>
        <%@include file="/WEB-INF/css/addProducts.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="add-product">
    <c:if test="${not empty errors}">
        <div class="alert alert-danger">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/manager/products/add" method="post" enctype="multipart/form-data">
        <label for="name" class="form-label"><fmt:message key="product.adding.name" bundle="${lang}"/></label>
        <input class="form-control" type="text" id="name" minlength="5" name="title"
               placeholder="<fmt:message key='product.adding.name.placeholder' bundle='${lang}'/>" required>

        <label for="category" class="form-label"><fmt:message key="product.adding.category" bundle="${lang}"/></label>
        <input class="form-control" type="text" id="category" minlength="3" name="category"
               placeholder="<fmt:message key='product.adding.category.placeholder' bundle='${lang}'/>" required>

        <label for="description" class="form-label"><fmt:message key="product.adding.description"
                                                                 bundle="${lang}"/></label>
        <textarea class="form-control" id="description" minlength="10" name="description" required></textarea>

        <label for="amount" class="form-label"><fmt:message key="product.adding.amount" bundle="${lang}"/></label>
        <input class="form-control" id="amount" type="number" min="20"
               placeholder="<fmt:message key='product.adding.amount.placeholder' bundle='${lang}'/>" step="1"
               name="amount" required>

        <label for="coast" class="form-label"><fmt:message key="product.adding.coast" bundle="${lang}"/></label>
        <input type="number" class="form-control" id="coast" min="0.01"
               placeholder="<fmt:message key='product.adding.coast.placeholder' bundle='${lang}'/>" name="coast"
               step="0.01" required>

        <label for="file-input" class="form-label"><fmt:message key="product.adding.file" bundle="${lang}"/></label>
        <div id="file-input">
            <div id="file-window">
                <input type="file" accept="image/jpeg, image/png" id="file" name="files" multiple>
            </div>
            <div>
                <button type="button" id="plus-button">+</button>
                <button type="button" id="minus-button">-</button>
            </div>
        </div>
        <div class="d-flex mt-4">
            <input type="submit" class="btn btn-primary"
                   value="<fmt:message key='product.adding.submit' bundle='${lang}'/>">
            <input type="button" class="btn btn-danger mx-3"
                   onclick="window.location.href='${pageContext.request.contextPath}/products/manager'"
                   value="<fmt:message key='product.adding.refuse' bundle='${lang}'/>">
        </div>
    </form>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/addProduct.js"%>
</script>
</html>
