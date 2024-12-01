<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>header</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <style>
        .onlineShopHeaderTitle {
            color: white;
            font-size: 20px;
            font-family: Arial;
            text-align: center;
            border-style: solid;
            border-width: 2px;
            border-radius: 5px;
            border-color: orange;
            padding: 2px;
            padding-inline: 5px;
            margin: 2px;
            background-color: orange;
        }

        .image {
            width: 60px;
            height: 60px;
            margin-left: 10px;
            background-color: #343a40;
        }

        .profileImage {
            width: 50px;
            height: 50px;
            margin-left: 10px;
            background-color: white;
            padding: 0;
            border-style: solid;
            border-color: white;
            border-radius: 50%;
        }

        .circle {
            position: absolute;
            top: 10px;
            right: 10px;
            width: 20px;
            height: 20px;
            background-color: blue;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            font-size: 12px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg bg-dark">
        <div class="container-fluid">
            <p class="onlineShopHeaderTitle">OnlineShop</p>
            <c:choose>
                <c:when test="${sessionScope.user==null}">
                    <input type="button" class="btn btn-success" value="Войти"
                           onclick="window.location.href='${pageContext.request.contextPath}/login'">
                </c:when>
                <c:otherwise>
                    <div>
                        <div style="position: relative; display: inline-block;">
                            <img class="image"
                                 src="https://png.klev.club/uploads/posts/2024-04/png-klev-club-z0k5-p-korzina-belaya-png-9.png">
                            <c:choose>
                                <c:when test="${!sessionScope.user.getCarts().isEmpty()}">
                                    <div class="circle">${sessionScope.user.getCarts().size()}</div>
                                </c:when>
                            </c:choose>
                        </div>
                        <img class="profileImage"
                             src="https://cdn.icon-icons.com/icons2/1993/PNG/512/account_avatar_face_man_people_profile_user_icon_123197.png">
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
