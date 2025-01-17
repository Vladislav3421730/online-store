<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}"/>
    <fmt:setBundle basename="messages" var="lang"/>
    <title><fmt:message key="admin.panel.title" bundle="${lang}"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container mt-4">
    <form action="${pageContext.request.contextPath}/admin/search" method="get" class="w-100  mr-3">
        <div class="input-group mb-3">
            <input value="${requestScope.search}" type="text" class="form-control" id="search"
                   placeholder="<fmt:message key='admin.search.placeholder' bundle='${lang}'/>"
                   name="search"/>
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit"><fmt:message key="admin.search.button" bundle="${lang}"/></button>
            </div>
        </div>
    </form>
    <c:choose>
        <c:when test="${requestScope.users.isEmpty()}">
            <h3><fmt:message key="admin.no.users.found" bundle="${lang}"/></h3>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="admin.id" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="admin.email" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="admin.username" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="admin.ban" bundle="${lang}"/></th>
                    <th scope="col"><fmt:message key="admin.manager" bundle="${lang}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${user.getId()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getUsername()}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/bun" method="post">
                                <input type="hidden" name="userId" value="${user.getId()}">
                                <c:choose>
                                    <c:when test="${user.isBun()}">
                                        <input type="submit" class="btn btn-success" value="<fmt:message key='admin.unban' bundle='${lang}'/>">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" class="btn btn-danger" value="<fmt:message key='admin.ban' bundle='${lang}'/>">
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/role/manager" method="post">
                                <input type="hidden" name="userId" value="${user.getId()}">
                                <c:choose>
                                    <c:when test="${user.isManager()}">
                                        <input type="submit" class="btn btn-danger" value="<fmt:message key='admin.remove.manager' bundle='${lang}'/>">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" class="btn btn-success" value="<fmt:message key='admin.make.manager' bundle='${lang}'/>">
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
