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
            <img src="data:image/jpeg;base64,${book.picture}" alt="img"/>
        </div>
        <div class="block-book-info">
            <div class="book-title">
                <span class="title-name">${book.title}</span>
            </div>
            <br>
            <div class="book-author">
                <span class="author-name">${book.author}, ${book.publishYear}</span>
                <%--                <br>--%>
                <%--                <span class="category-name">${book.category.name}</span>--%>
            </div>
            <hr/>
            <div class="book-description" style="margin-left: 20px">
                <span class="description-name">${book.description}</span>
            </div>

        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>