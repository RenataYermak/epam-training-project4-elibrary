<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Books</title>
    <!-- Styles -->
    <link rel="stylesheet" href="../css/header-style.css">
    <link rel="stylesheet" href="../css/sidebar-style.css">
    <link rel="stylesheet" href="../css/table-style.css">
    <link rel="stylesheet" href="../css/content-style.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/card.css">
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
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="page.title.books"/></h2>
        </div>
        <div class="content-search">
            <form action="controller" method="get" name="searchForm">
                <label for="site-search">
                    <input class="search-form" type="search" name="searchQuery" id="site-search"
                           placeholder=
                           <fmt:message key="table.button.search"/> pattern="^[\p{L}\d-.]{1,25}$">
                </label>
                <button class="btn" style=" margin: -3px 10px 10px 0px" type="submit" name="command"
                        value="book_search"><i class="fas fa-search"></i>
                </button>
            </form>
        </div>
        <div class="content-search">
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <form action="controller" method="get">
                    <button class="btn" style=" margin: -3px 5px 10px 5px" type="submit" name="command"
                            value="add_book_page"><i class='far fa-address-book'></i>
                        <fmt:message key="book.button.add_book"/>
                    </button>
                </form>
            </c:if>
        </div>
        <c:if test="${ warningMessagePassMismatch != null || successMessageBookOrdered!= null  }">
            <div class="content-search" style="margin-top: 0px;margin-bottom: 0px; margin-right: 110px">
                <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                <p class="content-msg cnt-msg-success">${successMessageBookOrdered}</p>
            </div>
        </c:if>
    </div>
    <hr/>
    <c:if test="${fn:length(books) > 0}">
        <c:forEach items="${books}" var="book">
            <div class="product">
                <div class="image">
                    <img src="data:image/jpeg;base64,${book.picture}" alt="img">
                </div>
                <div class="info">
                    <div class="main-title">
                        <form action="controller">
                            <label>
                                <input hidden name="bookId" value="${book.id}">
                            </label>
                            <a href="${pageContext.request.contextPath}/controller?bookId=${book.id}&command=book_view_page">
                                <span class="text-title">${book.title}</span></a>
                        </form>
                    </div>
                    <br>
                    <span class="info-item">${book.author.name} </span>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <br>
                        <span class="info-item">${book.category.name}, ${book.publishYear} </span>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <form action="controller">
                            <label>
                                <input hidden name="bookId" value="${book.id}">
                            </label>
                            <button class="btn" style="width: 100%" type="submit" name="command" value="find_book">
                                <fmt:message key="table.button.edit"/>
                            </button>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                        <div class="book-author">
                            <span class="author-name"><fmt:message key="table.label.available"/> ${book.number}</span>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                        <form action="controller" method="post">
                            <input hidden name="bookId" value="${book.id}">
                            <input hidden name="userId" value="${sessionScope.authUser.id}">
                            <c:choose>
                                <c:when test="${book.number > 0}">
                                    <input hidden name="bookTitle" value="${book.title}">
                                    <select name="type" style="width: 100%;">
                                        <option value="reading_room" selected="selected"><fmt:message
                                                key="table.label.reading_room"/></option>
                                        <option value="season_ticket"><fmt:message
                                                key="table.label.season_ticket"/></option>
                                    </select>
                                    <button class="btn" style="width: 100%" type="submit" name="command"
                                            value="order_book">
                                        <fmt:message key="table.button.order"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input hidden name="bookTitle" value="${book.title}">
                                    <select name="type" style="width: 100%;" disabled>
                                        <option value="reading_room" selected="selected"><fmt:message
                                                key="table.label.reading_room"/></option>
                                        <option value="season_ticket"><fmt:message
                                                key="table.label.season_ticket"/></option>
                                    </select>
                                    <button disabled class="btn" style="width: 100%" type="submit" name="command"
                                            value="order_book">
                                        <fmt:message key="table.button.order"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <br>
    <div class="container">
        <div class="rows" style="justify-content:start; margin-top: 10px">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:if test="${requestScope.page > 1}">
                        <li><a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=find_books&page=${requestScope.page-1}">
                            <span aria-hidden="true">&laquo;</span></a></li>
                    </c:if>
                    <c:if test="${requestScope.number_of_pages  > 1}">
                        <c:forEach begin="1" end="${requestScope.number_of_pages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.page eq i}">
                                    <li><a class="page-link">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=find_books&page=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                            <li><a class="page-link"
                                   href="${pageContext.request.contextPath}/controller?command=find_books&page=${requestScope.page+1}">
                                <span aria-hidden="true">&raquo;</span></a></li>
                        </c:if>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
    <c:if test="${fn:length(books) == 0}">
        <p class="info-style">${warningMessageBookSearch} <span class="info-style-srh">"${searchQuery}"</span></p>
    </c:if>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>

