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
<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>
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
            <div class="content-search">
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <form action="controller" method="get">
                        <button class="btn" style=" margin: -3px 5px 10px 5px" type="submit" name="command"
                                value="add_author_page"><i class='far fa-address-book'></i>
                            <fmt:message key="book.button.add_author"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
        <hr/>
        <form style="margin-left: 10px" action="${pageContext.request.contextPath}/controller?command=add_book"
              method="post"
              enctype="multipart/form-data">
            <table class="content-table-main">
                <tr>
                    <input type="file" name="picture" accept=".png, .jpg, .jpeg"/>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.title"/></td>
                    <td><input class="book-form" type="text" name="title" required pattern="^[\p{L}\d\s-.']{2,25}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.title"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.title"/>' : '');"
                               value="${book.title}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.author"/></td>
                    <td>
                        <select name="author" style="width: 236px; margin:2px 0 2px 0">
                            <c:forEach items="${authors}" var="author">
                                <option selected value="${author.id}">${author.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.category"/></td>
                    <td>
                        <select name="category" style="width: 236px;margin:2px 0 2px 0">
                            <option value="fiction"><fmt:message key="book.category.fiction"/></option>
                            <option value="detective"><fmt:message key="book.category.detective"/></option>
                            <option value="novel"><fmt:message key="book.category.novel"/></option>
                            <option value="science"><fmt:message key="book.category.science"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.publish_year"/></td>
                    <td><input class="book-form" type="number" size="4" name="publishYear" min="1500" max="2022"
                               required pattern="^[0-9]$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.publishYear"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.publishYear"/>' : '');"
                               value="${book.publishYear}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.number"/></td>
                    <td><input class="book-form" type="number" name="number" min="0" max="100" required
                               pattern="^[0-9]{1,3}$"
                               oninvalid="this.setCustomValidity('<fmt:message
                                       key="validation.book.add.number"/>')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message
                                       key="validation.book.add.number"/>' : '');"
                               value="${book.number}"></td>
                </tr>
                <tr>
                    <td class="content-table"><fmt:message key="book.label.description"/></td>
                    <td><textarea class="book-form" style="height: 70px" name="description" type="text" minlength="10"
                                  maxlength="3000" required
                                  pattern="^[\p{L}\d\p{S}\p{So}\p{P}\s\f\n\r\t\v]$>"> ${book.description} </textarea>
                    </td>
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
                <button class="btn" style="width: 57%;margin-left: 145px" type="submit" name="command"
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
<%@ include file="include/footer.jsp" %>
</body>
</html>