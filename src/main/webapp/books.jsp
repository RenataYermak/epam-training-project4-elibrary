<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>Books</title>
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
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="page.title.books"/></h2>
        </div>
        <div class="content-search">
            <form action="controller" method="get" name="searchForm">
                <label for="site-search">
                    <input type="search" name="searchQuery" id="site-search"
                           placeholder="<fmt:message key="table.label.book_search"/>"
                           required pattern="^[\p{L}\d-.]{2,25}$">
                </label>
                <button type="submit" name="command" value="book_search">
                    <fmt:message key="table.button.search"/></button>
            </form>
        </div>
        <div class="content-search">
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <form action="addBook.jsp">
                    <button type="submit"><i class='far fa-address-book'></i> <fmt:message key="book.button.add_book"/>
                    </button>
                </form>
            </c:if>
        </div>
    </div>
    <hr/>
    <c:if test="${fn:length(books) > 0}">
        <table class="table">
            <tr class="row header green">
                <th class="cell"><fmt:message key="table.label.title"/></th>
                <th class="cell"><fmt:message key="table.label.author"/></th>
                <th class="cell"><fmt:message key="table.label.category"/></th>
                <th class="cell"><fmt:message key="table.label.publish_year"/></th>
                <th class="cell"><fmt:message key="table.label.rating"/></th>
                <th class="cell"><fmt:message key="table.label.number"/></th>
                <th class="cell"><fmt:message key="table.label.action"/></th>
            </tr>
            <c:forEach items="${books}" var="book">
                <tr class="row">
                    <td class="cell"><a href="/controller?command=find_book&bookId=${book.id}">${book.title}</a></td>
                    <td class="cell">${book.author}</td>
                    <td class="cell">${book.category.name}</td>
                    <td class="cell">${book.publishYear}</td>
                    <td class="cell">${book.overallRating}</td>
                    <td class="cell">${book.number}</td>
                    <td class="cell" style="width: 100px">
                        <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                            <form action="controller">
                                <label>
                                    <input hidden name="bookId" value="${book.id}">
                                </label>
                                <button class="actionButton" type="submit" name="command" value="find_book">
                                    <fmt:message key="table.button.edit"/>
                                </button>
                                <button class="actionButton orderButton" type="submit" name="command"
                                        value="order_book">
                                    <fmt:message key="table.button.order"/>
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                            <form action="controller" method="post">
                                <input hidden name="bookId" value="${book.id}">
                                <input hidden name="userId" value="${sessionScope.authUser.id}">
                                <c:choose>
                                    <c:when test="${book.number != 0}">
                                        <input hidden name="bookTitle" value="${book.title}">
                                        <select name="issue">
                                            <option value="reading_room" selected="selected"><fmt:message
                                                    key="table.label.reading_room"/></option>
                                            <option value="season_ticket"><fmt:message
                                                    key="table.label.season_ticket"/></option>
                                        </select>
                                        <button class="actionButton orderButton" type="submit" name="command"
                                                value="order_book">
                                            <fmt:message key="table.button.order"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <input hidden name="bookTitle" value="${book.title}">
                                        <select name="issue" disabled>
                                            <option value="reading_room" selected="selected"><fmt:message
                                                    key="table.label.reading_room"/></option>
                                            <option value="season_ticket"><fmt:message
                                                    key="table.label.season_ticket"/></option>
                                        </select>
                                        <button disabled class="actionButton orderButton" type="submit" name="command"
                                                value="order_book">
                                            <fmt:message key="table.button.order"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${fn:length(books) == 0}">
        <p class="info-style">${warningMessageBookSearch} <span class="info-style-srh">"${searchQuery}"</span></p>
    </c:if>
    <c:if test="${orderId != null}">
        <p class="info-style"><fmt:message key="books.info.booked_success"/> <span
                class="info-style-srh">${bookTitle}</span> <fmt:message key="books.info.book"/></p>
    </c:if>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>

