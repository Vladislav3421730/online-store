<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title>404 - Not found</title>
    <style>
        <%@include file="/WEB-INF/css/error.css"%>
    </style>
</head>
<body>
<div class="forbidden-container">
    <h1>404</h1>

    <p>message: ${message}</p>
    <a href="${pageContext.request.contextPath}/products">
        <fmt:message key='forbidden.back' bundle='${lang}'/></a>
</div>
</body>
</html>
