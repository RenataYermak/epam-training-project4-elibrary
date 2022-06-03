<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>Add Book</title>
    <!-- Styles -->
    <link rel="stylesheet" href="../css/header-style.css">
    <link rel="stylesheet" href="../css/sidebar-style.css">
    <link rel="stylesheet" href="../css/table-style.css">
    <link rel="stylesheet" href="../css/content-style.css">
    <link rel="stylesheet" href="../css/style.css">
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
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<div id="content">
    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
        <div class="content-main">
            <div class="content-title">
                <h2><fmt:message key="book.title.add_book"/></h2>
            </div>
            <div class="content-search">
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/controller?command=find_books">
                        <i class='far fa-arrow-alt-circle-left'></i> <fmt:message key="book.back.book_list"/>
                    </a>
                </c:if>
            </div>
        </div>
        <hr/>
        <form action="controller" method="post">
            <table class="content-table-main">
                <tr>
                    <td class="content-table"><fmt:message key="book.label.title"/></td>
                    <td><input type="text" name="title" required pattern="^[\p{L}\d-]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.title"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.title"/>' : '');"
                               value="${book.title}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.author"/></td>
                    <td><input type="text" name="author" required pattern="^[\p{L}\d-]{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.author"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.author"/>' : '');"
                               value="${book.author}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.category"/></td>
                    <td>
                        <select name="category">
                            <option value="sci_fi"><fmt:message key="book.category.sci_fi"/></option>
                            <option value="detective"><fmt:message key="book.category.detective"/></option>
                            <option value="novel" selected="selected"><fmt:message key="book.category.novel"/></option>
                            <option value="science" selected="selected"><fmt:message
                                    key="book.category.science"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.publish_year"/></td>
                    <td><input type="number" size="4" name="publishYear" min="1500" max="2022" required
                               pattern="^[0-9]$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.publishYear"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.publishYear"/>' : '');"
                               value="${book.publishYear}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.number"/></td>
                    <td><input type="number" name="number" min="1" max="100" required pattern="^[0-9]{1,3}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.number"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.number"/>' : '');"
                               value="${book.number}"></td>
                </tr>
            </table>
            <c:if test="${successMessageBookCreate != null || warningMessagePassMismatch != null}">
                <div class="content-submit-btn-main">
                    <p class="content-msg cnt-msg-success">${successMessageBookCreate}</p>
                    <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                </div>
            </c:if>
            <div class="content-submit-btn-main">
                <input hidden name="bookId" value="${book.id}">
                <button class="content-submit-btn" type="submit" name="command" value="find_book"><fmt:message
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
<%@ include file="footer.jsp" %>
</body>
</html>