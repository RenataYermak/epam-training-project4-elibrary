<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
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
                <h2><fmt:message key="page.title.add_author"/></h2>
            </div>
            <div class="content-search">
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/controller?command=add_book_page">
                        <i class='far fa-arrow-alt-circle-left'></i> <fmt:message key="book.back.add_book"/>
                    </a>
                </c:if>
            </div>
        </div>
        <hr/>
        <div>
            <form action="controller" method="get">
                <td class="content-table"><fmt:message key="book.label.author"/></td>
                <td><input class="book-form" type="text" name="authorName" required pattern="^[\p{L}\d\s-.']{2,25}$"
                           oninvalid="this.setCustomValidity('<fmt:message
                                   key="validation.book.add.author"/>')"
                           onchange="this.setAttribute('value', this.value);
                                   this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                   key="validation.book.add.author"/>' : '');"
                           value="${author.name}"></td>
                </tr>
                <div class="content-submit-btn-main">
                    <input hidden name="authorId" value="${author.id}">
                    <button class="btn" style="width: 57%;margin-left: 80px" type="submit" name="command"
                            value="add_author">
                        <fmt:message key="book.button.add"/>
                    </button>
                </div>
                <c:if test="${successMessageAuthorCreate != null || warningMessagePassMismatch != null}">
                    <div class="content-submit-btn-main" style="margin-left: -40px">
                        <p class="content-msg cnt-msg-success">${successMessageAuthorCreate}</p>
                        <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                    </div>
                </c:if>
            </form>
            <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                <p class="info-style"><fmt:message key="register.info.permission"/></p>
            </c:if>
        </div>
    </c:if>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
