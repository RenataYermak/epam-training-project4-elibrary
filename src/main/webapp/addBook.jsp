<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>Add Book</title>
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
    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
        <div class="content-main">
            <div class="content-title">
                <h2><fmt:message key="book.title.add_book"/></h2>
            </div>
        </div>
        <hr/>
        <form action="controller" method="post">
            <table class="content-table-main">
                <tr>
                    <td class="content-table"><fmt:message key="book.label.title"/></td>
                    <td><input type="text" name="title" required pattern="^[\p{L}\d-.]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.user.registration.login"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.user.registration.login"/>' : '');"
                               value="${book.title}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.author"/></td>
                    <td><input type="text" name="author" required pattern="^[\p{L}\d-.]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.user.registration.firstname"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.user.registration.firstname"/>' : '');"
                               value="${book.author}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.category"/></td>
                    <td>
                        <select name="category">
                            <option value="sci_fi"><fmt:message key="book.category.sci_fi"/></option>
                            <option value="detective"><fmt:message key="book.category.detective"/></option>
                            <option value="novel"><fmt:message key="book.category.novel"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.publish_year"/></td>
                    <td><input type="number" size="4" name="year" min = "1500" required pattern="^[0-9]$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.user.registration.firstname"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.user.registration.firstname"/>' : '');"
                               value="${book.publishYear}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.number"/></td>
                    <td><input type="number" name="number" min = "0" required pattern="^[0-9]{1,3}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.user.registration.password"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.user.registration.password"/>' : '');"
                               value="${book.number}"></td>
                </tr>
            </table>
            <c:if test="${successMessageUserUpdated != null || warningMessagePassMismatch != null}">
                <div class="content-submit-btn-main">
                    <p class="content-msg cnt-msg-success">${successMessageUserUpdated}</p>
                    <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                </div>
            </c:if>
            <div class="content-submit-btn-main">
                <input hidden name="bookId" value="${book.id}">
                <button class="content-submit-btn" type="submit" name="command" value="find_user"><fmt:message
                        key="book.button.cancel"/></button>
                <button class="content-submit-btn content-sub-btn-s-style" type="submit" name="command"
                        value="add_book">
                    <fmt:message key="book.button.add"/>
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