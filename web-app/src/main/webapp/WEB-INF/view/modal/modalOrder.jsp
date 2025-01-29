<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="addressModal.title" bundle="${lang}" /></title>
    <style>
        select {
            border: 1px solid black;
            border-radius: 5px;
            padding: 3px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3><fmt:message key="addressModal.enterAddress" bundle="${lang}" /></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/user/cart" method="post">
                    <c:choose>
                        <c:when test="${!requestScope.addresses.isEmpty()}">
                            <select id="address-select">
                                <option value=""><fmt:message key="addressModal.choosePrevious" bundle="${lang}" /></option>
                                <c:forEach var="address" items="${requestScope.addresses}">
                                    <option
                                            value="${address.getId()}"
                                            data-region="${address.getRegion()}"
                                            data-town="${address.getTown()}"
                                            data-exactAddress="${address.getExactAddress()}"
                                            data-postalCode="${address.getPostalCode()}">
                                            ${address.getRegion()} ${address.getTown()} ${address.getExactAddress()} ${address.getPostalCode()}
                                    </option>
                                </c:forEach>
                            </select>
                        </c:when>
                    </c:choose>
                    <div class="form-group mb-2 mt-2">
                        <label for="region"><fmt:message key="addressModal.region" bundle="${lang}" /></label>
                        <input type="text" class="form-control mt-1" name="region" id="region"
                               placeholder="<fmt:message key='addressModal.enterRegion' bundle='${lang}' />" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="town"><fmt:message key="addressModal.town" bundle="${lang}" /></label>
                        <input type="text" class="form-control mt-1" name="town" id="town"
                               placeholder="<fmt:message key='addressModal.enterTown' bundle='${lang}' />" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="exactAddress"><fmt:message key="addressModal.exactAddress" bundle="${lang}" /></label>
                        <input type="text" class="form-control mt-1" name="exactAddress"
                               id="exactAddress" placeholder="<fmt:message key='addressModal.enterExactAddress' bundle='${lang}' />" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="postalCode"><fmt:message key="addressModal.postalCode" bundle="${lang}" /></label>
                        <input type="text" class="form-control mt-1" name="postalCode"
                               id="postalCode" placeholder="<fmt:message key='addressModal.enterPostalCode' bundle='${lang}' />">
                    </div>
                    <input type="hidden" id="addressId" name="id">
                    <input type="hidden" name="totalCoast" value="${requestScope.totalCoast}"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><fmt:message key="addressModal.close" bundle="${lang}" /></button>
                        <input type="submit" value="<fmt:message key='addressModal.confirm' bundle='${lang}' />" class="btn btn-primary"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    <%@include file="/WEB-INF/script/chooseAddress.js"%>
</script>
</body>
</html>
