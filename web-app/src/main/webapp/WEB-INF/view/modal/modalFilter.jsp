<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="filterModal.title" bundle="${lang}"/></title>
    <style>
        <%@include file="/WEB-INF/css/modal.css"%>
    </style>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3><fmt:message key="filterModal.title" bundle="${lang}"/></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/products/filter" method="get">
                    <label for="category" class="form-label"><fmt:message key="filterModal.category"
                                                                          bundle="${lang}"/></label><br>
                    <select id="category" name="category">
                        <option value="" selected><fmt:message key="filterModal.selectCategory"
                                                               bundle="${lang}"/></option>
                        <option value="Игрушки"><fmt:message key="filterModal.toys" bundle="${lang}"/></option>
                        <option value="Мужская одежда"><fmt:message key="filterModal.menClothing"
                                                                    bundle="${lang}"/></option>
                        <option value="Женская одежда"><fmt:message key="filterModal.womenClothing"
                                                                    bundle="${lang}"/></option>
                        <option value="Для дома"><fmt:message key="filterModal.homeGoods" bundle="${lang}"/></option>
                        <option value="Обувь"><fmt:message key="filterModal.shoes" bundle="${lang}"/></option>
                        <option value="Инструменты"><fmt:message key="filterModal.tools" bundle="${lang}"/></option>
                        <option value="Техника"><fmt:message key="filterModal.electronics" bundle="${lang}"/></option>
                    </select><br>
                    <label for="sort" class="form-label mt-2"><fmt:message key="filterModal.sortBy"
                                                                           bundle="${lang}"/></label><br>
                    <select id="sort" name="sort">
                        <option selected><fmt:message key="filterModal.selectSort" bundle="${lang}"/></option>
                        <option value="expensive"><fmt:message key="filterModal.sortExpensive"
                                                               bundle="${lang}"/></option>
                        <option value="cheap"><fmt:message key="filterModal.sortCheap" bundle="${lang}"/></option>
                        <option value="alphabet"><fmt:message key="filterModal.sortAlphabet" bundle="${lang}"/></option>
                    </select><br>
                    <div class="d-flex align-items-center mt-2">
                        <div class="mt-2" style="margin-right: 10px">
                            <label for="minPrice" class="form-label"><fmt:message key="filterModal.minPrice"
                                                                                  bundle="${lang}"/>:</label><br>
                            <input type="number" step="0.1" min="0" id="minPrice" name="minPrice" placeholder="0">
                        </div>
                        <div class="mt-2">
                            <label for="maxPrice" class="form-label"><fmt:message key="filterModal.maxPrice"
                                                                                  bundle="${lang}"/>:</label>
                            <input type="number" step="0.1" id="maxPrice" min="0" name="maxPrice"
                                   placeholder="10000"><br>
                        </div>
                    </div>
                    <input type="hidden" name="search-filter" id="search-filter">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><fmt:message
                                key="filterModal.close" bundle="${lang}"/></button>
                        <button type="submit" class="btn btn-primary"><fmt:message key="filterModal.applyFilters"
                                                                                   bundle="${lang}"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
