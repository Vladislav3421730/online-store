<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'ru'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title>403 - Forbidden</title>
    <style>
        <%@include file="/WEB-INF/css/error.css"%>
    </style>
</head>
<body>
<div class="forbidden-container">
    <h1>403</h1>
    <p><fmt:message key='forbidden.access' bundle='${lang}'/></p>
    <a href="${pageContext.request.contextPath}/products">
        <fmt:message key='forbidden.back' bundle='${lang}'/></a>
</div>
</body>
</html>
