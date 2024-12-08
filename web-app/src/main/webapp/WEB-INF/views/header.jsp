<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>header</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <style>
        <%@include file="/WEB-INF/css/header.css"%>
        .button-container {
            display: flex;
            align-items: center;
            justify-content: center;
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-dark">
        <div class="container-fluid">
            <a href="${pageContext.request.contextPath}/">
                <p class="onlineShopHeaderTitle">OnlineShop</p>
            </a>
            <c:choose>
                <c:when test="${sessionScope.user==null}">
                    <input type="button" class="btn btn-success" value="Войти"
                           onclick="window.location.href='${pageContext.request.contextPath}/login'">
                </c:when>
                <c:otherwise>
                    <div class="d-flex">
                        <c:choose>
                            <c:when test="${sessionScope.user.isAdmin()}">
                                <div class="button-container">
                                    <input type="button" class="btn btn-danger" value="Панель админа"
                                           onclick="window.location.href='${pageContext.request.contextPath}/admin/panel'">
                                </div>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${sessionScope.user.isManager()}">
                                <div class="button-container">
                                    <div class="dropdown mx-2">
                                        <button class="btn btn-secondary dropdown-toggle" type="button"
                                                id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false">
                                            Панель менеджера
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/manager/orders">Заказы</a>
                                            <a class="dropdown-item"
                                               href="${pageContext.request.contextPath}/manager/products">Продукты</a>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                        <div style="position: relative; display: inline-block;">
                            <a href="${pageContext.request.contextPath}/user/cart">
                                <img class="image"
                                     src="https://png.klev.club/uploads/posts/2024-04/png-klev-club-z0k5-p-korzina-belaya-png-9.png">
                                <c:choose>
                                    <c:when test="${!sessionScope.user.getCarts().isEmpty()}">
                                        <div class="circle">${sessionScope.user.getCarts().size()}</div>
                                    </c:when>
                                </c:choose>
                            </a>
                        </div>
                        <a href="${pageContext.request.contextPath}/user/account">
                            <img class="profileImage"
                                 src="https://cdn.icon-icons.com/icons2/1993/PNG/512/account_avatar_face_man_people_profile_user_icon_123197.png">
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</header>

</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</html>
