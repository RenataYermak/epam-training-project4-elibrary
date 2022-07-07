<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Book</title>
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
            <h2><fmt:message key="page.title.book"/></h2>
        </div>
        <div class="content-search">
            <a href="${pageContext.request.contextPath}/controller?command=find_books">
                <i class='far fa-arrow-alt-circle-left'></i> <fmt:message key="book.back.book_list"/>
            </a>
        </div>
    </div>
    <hr/>
    <div class="block-book">
        <div class="image-book">
            <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                <form action="controller" method="post" enctype="multipart/form-data">
                    <div class="content-edit">
                        <input style="width: 230px; margin: 0px 0 0 10px" type="file" name="picture"
                               accept=".png, .jpg, .jpeg"/>
                    </div>
                    <div class="content-edit">
                        <input hidden name="bookId" value="${book.id}">
                        <button class="btn" style="width: 98px; margin: 0px 0 0 5px"  type="submit"
                                name="command"
                                value="edit_book_picture"><fmt:message key="book.button.edit"/>
                        </button>
                    </div>
                    <c:if test="${successMessageBookUpdated != null || warningMessagePassMismatch != null}">
                        <div class="content-submit-btn-main">
                            <p class="content-msg cnt-msg-success">${successMessageBookUpdated}</p>
                            <p class="content-msg cnt-msg-error">${warningMessagePassMismatch}</p>
                        </div>
                    </c:if>
                </form>
            </c:if>
            <img src="data:image/jpeg;base64,${book.picture}" alt="img"/>
        </div>
        <div class="block-book-info">
            <div class="book-title">
                <span class="title-name">${book.title}</span>
            </div>
            <br>
            <div class="book-author">
                <span class="author-name">${book.author.name}, ${book.publishYear}</span>
            </div>
            <hr/>
            <div class="book-description" style="margin-left: 20px">
                <span class="description-name">${book.description}</span>
            </div>
            <hr/>
            <div class="block-book-order">
                <div class="book-author">
                    <span class="author-name"><b><fmt:message key="table.label.available"/></b> ${book.number}</span>
                </div>
                <div>
                    <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                        <form action="controller" method="post">
                            <input hidden name="bookId" value="${book.id}">
                            <input hidden name="userId" value="${sessionScope.authUser.id}">
                            <c:choose>
                                <c:when test="${book.number != 0}">
                                    <input hidden name="bookTitle" value="${book.title}">
                                    <select name="type" style="width: 115px; margin-top: 10px">
                                        <option value="reading_room" selected="selected"><fmt:message
                                                key="table.label.reading_room"/></option>
                                        <option value="season_ticket"><fmt:message
                                                key="table.label.season_ticket"/></option>
                                    </select>
                                    <br>
                                    <button class="btn" style="width: 115px" type="submit" name="command"
                                            value="order_book">
                                        <fmt:message key="table.button.order"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input hidden name="bookTitle" value="${book.title}">
                                    <select name="type" style="width: 112px; margin-top: 10px" disabled>
                                        <option value="reading_room" selected="selected"><fmt:message
                                                key="table.label.reading_room"/></option>
                                        <option value="season_ticket"><fmt:message
                                                key="table.label.season_ticket"/></option>
                                    </select>
                                    <br>
                                    <button disabled class="btn" style="width: 110px" type="submit" name="command"
                                            value="order_book">
                                        <fmt:message key="table.button.order"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>