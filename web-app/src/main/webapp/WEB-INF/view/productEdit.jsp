<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="editProduct.title" bundle="${lang}"/> ${requestScope.product.getTitle()}</title>
    <style>
        <%@include file="/WEB-INF/css/addProducts.css"%>
        <%@include file="/WEB-INF/css/buttons.css"%>
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="add-product">
    <form action="${pageContext.request.contextPath}/manager/products/edit/${requestScope.product.getId()}"
          method="post" enctype="multipart/form-data">

        <c:if test="${not empty errors}">
            <div class="alert alert-danger mb-2 mt-2">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <input type="hidden" value="${requestScope.product.getId()}" name="id">

        <label for="name" class="form-label"><fmt:message key="editProduct.name" bundle="${lang}"/></label>
        <input class="form-control" value="${requestScope.product.getTitle()}" type="text" id="name" minlength="3"
               name="title" placeholder="<fmt:message key='editProduct.namePlaceholder' bundle='${lang}' />" required>

        <label for="category" class="form-label"><fmt:message key="editProduct.category" bundle="${lang}"/></label>
        <input class="form-control" value="${requestScope.product.getCategory()}" type="text" id="category"
               minlength="3" name="category"
               placeholder="<fmt:message key='editProduct.categoryPlaceholder' bundle='${lang}' />" required>

        <label for="description" class="form-label"><fmt:message key="editProduct.description"
                                                                 bundle="${lang}"/></label>
        <textarea class="form-control" id="description" minlength="10" name="description"
                  required>${requestScope.product.getDescription()}</textarea>

        <label for="amount" class="form-label"><fmt:message key="editProduct.amount" bundle="${lang}"/></label>
        <input class="form-control" value="${requestScope.product.getAmount()}" id="amount" type="number" min="20"
               placeholder="<fmt:message key='editProduct.amountPlaceholder' bundle='${lang}' />" step="1" name="amount"
               required>

        <label for="coast" class="form-label"><fmt:message key="editProduct.coast" bundle="${lang}"/></label>
        <input type="number" value="${requestScope.product.getCoast()}" class="form-control" id="coast" min="0.01"
               placeholder="<fmt:message key='editProduct.coastPlaceholder' bundle='${lang}' />" name="coast"
               step="0.01" required>

        <label for="file-input" class="form-label"><fmt:message key="editProduct.fileInput" bundle="${lang}"/></label>
        <p><fmt:message key="editProduct.fileExplanation" bundle="${lang}"/></p>

        <div id="file-input">
            <div id="file-window">
                <c:forEach var="item" items="${requestScope.product.getImageList()}" varStatus="status">
                    <input type="file" class="mt-1 mb-1" accept="image/jpeg, image/png" id="file${status.index+1}"
                           name="files" multiple>
                </c:forEach>
            </div>
            <div>
                <button type="button" id="plus-button">+</button>
                <button type="button" id="minus-button">-</button>
            </div>
        </div>
        <div class="d-flex mt-4">
            <input type="submit" class="btn btn-success"
                   value="<fmt:message key='editProduct.submit' bundle='${lang}' />">
            <input type="button" class="btn btn-danger mx-3"
                   onclick="window.location.href='${pageContext.request.contextPath}/manager/products'"
                   value="<fmt:message key='product.adding.refuse' bundle='${lang}'/>">
        </div>
    </form>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/addProduct.js"%>
</script>

</html>
