<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="filterModal.title" bundle="${lang}" /></title>
    <style>
        <%@include file="/WEB-INF/css/modal.css"%>
    </style>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3><fmt:message key="filterModal.title" bundle="${lang}" /></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/manager/products" method="get">
                    <div class="d-flex align-items-center mt-2">
                        <div class="mt-2" style="margin-right: 10px">
                            <label for="minPrice" class="form-label"><fmt:message key="filterModal.minPrice" bundle="${lang}" />:</label><br>
                            <input type="number" step="0.1" value="${requestScope.filter.getMinPrice()}" min="0" id="minPrice" name="minPrice" placeholder="0">
                        </div>
                        <div class="mt-2">
                            <label for="maxPrice" class="form-label"><fmt:message key="filterModal.maxPrice" bundle="${lang}" />:</label>
                            <input type="number" value="${requestScope.filter.getMaxPrice()}" step="0.1" id="maxPrice" min="0" name="maxPrice" placeholder="10000"><br>
                        </div>
                    </div>
                    <input type="hidden" name="search-filter" id="search-filter">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><fmt:message key="filterModal.close" bundle="${lang}" /></button>
                        <button type="submit" class="btn btn-primary"><fmt:message key="filterModal.applyFilters" bundle="${lang}" /></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    <%@include file="/WEB-INF/script/modalSelectValue.js"%>
</script>
</body>
</html>
