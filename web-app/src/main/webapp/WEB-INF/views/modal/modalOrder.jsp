<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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
                <h3>Введите адрес доставки</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/user/cart" method="post">
                    <c:choose>
                        <c:when test="${!sessionScope.addresses.isEmpty()}">
                            <select id="address-select">
                                <option value="">Выбрать из предыдущих адресов доставки</option>
                                <c:forEach var="address" items="${sessionScope.addresses}">
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
                        <label for="region">Область</label>
                        <input type="text" class="form-control mt-1" name="region" id="region"
                               placeholder="Введите область" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="town">Город</label>
                        <input type="text" class="form-control mt-1" name="town" id="town"
                               placeholder="Введите город" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="exactAddress">Адресс</label>
                        <input type="text" class="form-control mt-1" name="exactAddress"
                               id="exactAddress" placeholder="Улица, дом, подъезд, квартира" required>
                    </div>
                    <div class="form-group mb-2">
                        <label for="postalCode">Почтовый индекс</label>
                        <input type="text" class="form-control mt-1" name="postalCode"
                               id="postalCode" placeholder="Введите почтовый индекс (необязательно)">
                    </div>
                    <input type="hidden" id="addressId" name="addressId">
                    <input type="hidden" name="totalCoast" value="${requestScope.totalCoast}"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Закрыть</button>
                        <input type="submit" value="Подтвердить" class="btn btn-primary"/>
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
