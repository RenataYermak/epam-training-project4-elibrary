<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>${user.firstName}</title>
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
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</head>
<body>
<%@ include file="/header.jsp" %>
<%@ include file="/sidebar.jsp" %>
<div id="content">
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="page.user.account_settings"/></h2>
        </div>
        <div class="content-search">
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/controller?command=find_books">
                    <i class='far fa-arrow-alt-circle-left'></i> <fmt:message key="user.back.user_list"/>
                </a>
            </c:if>
        </div>
        <div class="content-search">
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <form action="controller" method="get" >
                    <button type="submit" name="command" value="register_page"><i class='far fa-address-book'></i>
                        <fmt:message key="user.button.register_user"/>
                    </button>
                </form>
            </c:if>
        </div>
    </div>
    <hr/>
    <form action="controller" method="post">
        <table class="content-table-main">
            <tr>
                <td class="content-table"><fmt:message key="user.label.login"/></td>
                <td>
                    <c:choose>
                        <c:when test="${user.login == 'admin'}">
                            <input type="text" name="login" value="${user.login}" disabled="disabled">
                        </c:when>
                        <c:otherwise>
                            <input type="text" name="login" required pattern="[\w\d-]{2,25}$"
                                   oninvalid="this.setCustomValidity('<fmt:message
                                           key="validation.user.registration.login"/>')"
                                   onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ? 'error login' : '');"
                                   value="${user.login}">
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td class="content-table"><fmt:message key="user.label.first_name"/></td>
                <td><input type="text" name="firstName" required pattern="^[\p{L}]{2,25}$"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.firstname"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ?'<fmt:message
                                   key="validation.user.registration.firstname"/>' : '');"
                           value="${user.firstName}"></td>
            </tr>
            <tr>
                <td class="content-table"><fmt:message key="user.label.second_name"/></td>
                <td><input type="text" name="secondName" required pattern="^[\p{L}]{2,25}$"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.secondname"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ?
                                   '<fmt:message key="validation.user.registration.secondname"/>' : '');"
                           value="${user.secondName}"></td>
            </tr>
            <tr>
                <td class="content-table"><fmt:message key="user.label.password"/></td>
                <td><input type="password" name="password" required
                           pattern="(?=.*[\d])(?=.*[\p{Ll}])(?=.*[\p{Lu}])(?=\S+$).{8,49}" ;
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.password"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ?
                                   '<fmt:message key="validation.user.registration.password"/>' : '');"
                           value="${user.password}">
            </tr>
            <tr>
                <td class="content-table"><fmt:message key="user.label.email"/></td>
                <td><input type="text" name="email" required
                           pattern="^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,50})\.([a-z]{2,6}(?:\.[a-z]{2})?)$"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.user.registration.email"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ?
                                   '<fmt:message key="validation.user.registration.email"/>' : '');"
                           value="${user.email}"></td>
            </tr>
            <c:if test="${authUser.role == 'ADMIN' && authUser.login != 'admin'}">
                <tr>
                    <td class="content-table"><fmt:message key="user.label.role"/></td>
                    <td>
                        <select name="userRole">
                            <c:choose>
                                <c:when test="${user.role == 'ADMIN'}">
                                    <option value="admin" selected="selected"><fmt:message
                                            key="user.label.admin"/></option>
                                    <option value="user"><fmt:message key="user.label.user"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="admin"><fmt:message key="user.label.admin"/><</option>
                                    <option value="user" selected="selected"><fmt:message
                                            key="user.label.user"/></option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </td>
                </tr>
            </c:if>
            <%--<td><br/></td>--%>

            <%--            <tr>
                            <td class="content-table">Repeat Password:</td>
                            <td><input type="password" name="repeatPassword"></td>
                        </tr>--%>
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
                    key="user.button.cancel"/></button>
            <button class="content-submit-btn content-sub-btn-s-style" type="submit" name="command" value="edit_user">
                <fmt:message key="user.button.submit"/>
            </button>
        </div>
        <c:if test="${authUser.role == 'ADMIN'}">
            <div class="content-submit-btn-deactivate">
                <button class="content-submit-btn content-sub-btn-d-style"
                        type="submit" name="command" value="deactivate_user"><fmt:message
                        key="user.button.deactivate_account"/>
                </button>
            </div>
        </c:if>
    </form>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>

