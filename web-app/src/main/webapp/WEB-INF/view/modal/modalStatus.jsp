<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="orderStatus.title" bundle="${lang}" /></title>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3><fmt:message key="orderStatus.changeTitle" bundle="${lang}" /></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/orders/${requestScope.orderDto.getId()}" method="post">
                    <select class="form-control" id="select" name="status">
                        <option value="принят"><fmt:message key="orderStatus.accepted" bundle="${lang}" /></option>
                        <option value="в обработке"><fmt:message key="orderStatus.processing" bundle="${lang}" /></option>
                        <option value="в пути"><fmt:message key="orderStatus.inTransit" bundle="${lang}" /></option>
                        <option value="доставлен"><fmt:message key="orderStatus.delivered" bundle="${lang}" /></option>
                    </select>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><fmt:message key="orderStatus.close" bundle="${lang}" /></button>
                        <input type="submit" value="<fmt:message key='orderStatus.confirm' bundle='${lang}' />" class="btn btn-primary"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    <%@include file="/WEB-INF/script/viewStatusInSelect.js"%>
</script>
</html>
