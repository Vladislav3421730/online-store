<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Main page</title>

</head>
<jsp:include page="header.jsp"/>
<body>
<h1>Hello world</h1>
<button class="btn btn-success" onclick="window.location.href='${pageContext.request.contextPath}/product/add'">Добавить товар</button>
</body>
</html>