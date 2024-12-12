<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Изменение статуса заказа</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/manager/status/change" method="post">
                    <select  class="form-control" id="select" name="status">
                        <option value="ACCEPTED">принят</option>
                        <option value="IN_PROCESSING">в обработке</option>
                        <option value="IN_TRANSIT">в пути</option>
                        <option value="DELIVERED">доставлен</option>
                    </select>
                    <input type="hidden" value="${requestScope.order.getId()}" name="id">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Закрыть</button>
                        <input type="submit" value="Подтвердить" class="btn btn-primary"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
