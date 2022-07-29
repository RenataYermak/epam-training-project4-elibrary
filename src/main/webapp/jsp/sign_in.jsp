<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sing in </title>
    <!-- Styles -->
    <link rel="stylesheet" href="../css/header-style.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/sidebar-style.css">
    <link rel="stylesheet" href="../css/table-style.css">
    <link rel="stylesheet" href="../css/content-style.css">
    <!-- Icons -->
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <!-- Fonts Style -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Chakra+Petch&display=swap" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</head>
<body>
<header class="head">
    <div id="header">
        <div class="header-title">
            <h1 class="title"><span class="header-title-sp">e</span>Library</h1>
        </div>
        <div class="header-right-buttons">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="change_locale">
                <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
                <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
                <button class="btn" type="submit" name="locale" value="ru_RU">
                    <fmt:message key="header.lang.ru"/>
                </button>
                <button class="btn" type="submit" name="locale" value="en_EN">
                    <fmt:message key="header.lang.en"/>
                </button>
            </form>
        </div>
    </div>
</header>
<div id="sidebar">
    <div>
        <form name="loginForm" method="post" action="controller">
            <div class="signIn">
                <label>
                    <input class="login-form" style="margin-bottom: 5px" type="text" name="login" required
                           placeholder="login"
                           pattern="[\w\d-]{2,25}$"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.login"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                   key="validation.user.registration.login"/>' : '');"
                           value="${user.login}">
                </label>
                <br/>
                <label>
                    <input class="login-form" style="margin-bottom: 10px" type="password" id="pwd" name="password"
                           required
                           placeholder="password"
                           pattern="(?=.*[\d])(?=.*[\p{Ll}])(?=.*[\p{Lu}])(?=\S+$).{8,49}"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.password"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                   key="validation.user.registration.password"/>' : '');"
                           value="${user.password}">
                    <br/>
                    <input style="margin-bottom: 10px" type="checkbox" onclick="show()"><fmt:message
                        key="sign_in.button.show_password"/>
                </label>
                <br/>
                <button class="btn" type="submit" name="command" value="sign_in"><fmt:message
                        key="sign_in.button.sign_in"/></button>
                <p style="color: #ea6153">${errorMessageSignIn}</p>
            </div>
        </form>
    </div>
</div>
<div id="content">
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="sign_in.label.about_library"/></h2>
        </div>
    </div>
    <hr/>
    <div class="content-body">
        <p>
            <fmt:message key="sign_in.text1"/>
        </p>
        <p>
            <fmt:message key="sign_in.text2"/>
        </p>
    </div>
</div>
<%@ include file="include/footer.jsp" %>
</body>
</html>