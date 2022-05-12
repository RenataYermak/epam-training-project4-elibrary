<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>User Registration</title>
    <!-- Styles -->
    <link rel="stylesheet" href="css/header-style.css">
    <link rel="stylesheet" href="css/sidebar-style.css">
    <link rel="stylesheet" href="css/table-style.css">
    <link rel="stylesheet" href="css/content-style.css">
    <link rel="stylesheet" href="css/style.css">
    <!-- Icons -->
    <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
    <!-- Fonts Style -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Chakra+Petch&display=swap" rel="stylesheet">
</head>
<body>
<%@ include file="/header.jsp" %>
<%@ include file="/sidebar.jsp" %>
<div id="content">
    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
        <div class="content-main">
            <div class="content-title">
                <h2><fmt:message key="page.title.user_register"/></h2>
            </div>
            <div class="content-search">
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <a href="/controller?command=find_user&userId=${sessionScope.authUser.id}">
                        <i class='far fa-arrow-alt-circle-left'></i> <fmt:message key="register.inform.back_account"/>
                    </a>
                </c:if>
            </div>
        </div>
        <hr/>
        <form action="controller" method="post">
            <table class="content-table-main">
                <tr>
                    <td class="content-table"><fmt:message key="register.label.login"/></td>
                    <td><input type="text" name="login" required pattern="[\w\d-]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.user.registration.login"/>')"
                               onchange="this.setAttribute('value', this.value);
                               this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="validation.user.registration.login"/>' : '');"
                               value="${user.login}"
                               readonly onfocus="this.removeAttribute('readonly')"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="register.label.first_name"/></td>
                    <td><input type="text" name="firstName" required pattern="^[\wА-яЁё\s-]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.user.registration.firstname"/>')"
                               onchange="this.setAttribute('value', this.value);
                               this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="validation.user.registration.firstname"/>' : '');"
                               value="${user.firstName}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="register.label.second_name"/></td>
                    <td><input type="text" name="secondName" required pattern="^[\wА-яЁё\s-]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.user.registration.secondname"/>')"
                               onchange="this.setAttribute('value', this.value);
                               this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="validation.user.registration.secondname"/>' : '');"
                               value="${user.secondName}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="register.label.password"/></td>
                    <td><input type="password" name="password" required pattern="^[\w-]{8,16}$"
                               oninvalid="this.setCustomValidity('<fmt:message key="validation.user.registration.password"/>')"
                               onchange="this.setAttribute('value', this.value);
                               this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="validation.user.registration.password"/>' : '');"
                               value="${user.password}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="register.label.role"/></td>
                    <td>
                        <select name="userRole">
                            <option value="admin"><fmt:message key="register.label.admin"/></option>
                            <option value="user" selected="selected"><fmt:message key="register.label.user"/></option>
                        </select>
                    </td>
                </tr>
            </table>
            <c:if test="${successMessageUserUpdated != null || warningMessagePassMismatch != null}">
                <div class="content-submit-btn-main">
                    <p class="content-msg cnt-msg-success">${successMessageUserUpdated}</p>
                    <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                </div>
            </c:if>
            <div class="content-submit-btn-main">
                <input hidden name="userId" value="${user.id}">
                <button class="content-submit-btn" type="submit" name="command" value="find_user"><fmt:message
                        key="register.button.cancel"/></button>
                <button class="content-submit-btn content-sub-btn-s-style" type="submit" name="command"
                        value="registration">
                    <fmt:message key="register.button.register_user"/>
                </button>
            </div>
        </form>
    </c:if>
    <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
        <p class="info-style"><fmt:message key="register.info.permission"/></p>
    </c:if>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>
