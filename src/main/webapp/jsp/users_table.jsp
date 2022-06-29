<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<head>
    <title>Users</title>
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
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<div id="content">
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="page.title.users"/></h2>
        </div>
        <div class ="content-search">
            <form action="controller" method="get" name="searchForm">
                <label for="site-search">
                    <input class="search-form " type="search" name="searchQuery" id="site-search"
                           placeholder=<fmt:message key="table.button.search"/> pattern="^[\p{L}\d-.]{1,25}$">
                </label>
                <button class="btn" style=" margin: -3px 10px 10px 0px" type="submit" name="command"
                        value="book_search"><i class="fas fa-search"></i>
                </button>
            </form>
        </div>
    </div>
    <hr/>
    <c:if test="${fn:length(users) > 0}">
        <table class="table">
            <tr class="row-header-green">
                <th class="cell-main"><fmt:message key="users.table.label.login"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.first_name"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.second_name"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.email"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.role"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.activation_date"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.deactivation_date"/></th>
                <th class="cell-main"><fmt:message key="users.table.label.action"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr class="row">
                    <td class="cell">${user.login}</td>
                    <td class="cell">${user.firstName}</td>
                    <td class="cell">${user.secondName}</td>
                    <td class="cell">${user.email}</td>
                    <td class="cell">${user.role.name}</td>
                    <td class="cell">
                        <fmt:parseDate value="${user.activationDate}" pattern="y-M-dd'T'H:m" var="myParseDate"></fmt:parseDate>
                        <fmt:formatDate value="${myParseDate}" pattern="HH:mm:ss dd.MM.yyyy" /></td>
                    <td class="cell">
                        <fmt:parseDate value="${user.deactivationDate}" pattern="y-M-dd'T'H:m" var="myParseDate"></fmt:parseDate>
                        <fmt:formatDate value="${myParseDate}" pattern="HH:mm:ss dd.MM.yyyy" /></td>
                    <td class="cell">
                        <c:if test="${sessionScope.authUser.role == 'ADMIN' ||
                                    sessionScope.authUser.login == user.login}">
                            <form action="controller">
                                <label>
                                    <input hidden name="userId" value="${user.id}">
                                </label>
                                <button class="actionButton "style="margin-left: 5%" type="submit" name="command" value="find_user">
                                    <fmt:message key="users.button.edit"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <div class="container">
        <div class="rows" style="justify-content: start">
            <nav aria-label="pagination">
                <ul class="pagination">
                    <c:if test="${requestScope.page != 1}">
                        <li><a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=find_users&page=${requestScope.page-1}">
                            <span aria-hidden="true">&laquo;</span></a></li>
                    </c:if>
                    <c:forEach begin="1" end="${requestScope.number_of_pages}" var="i">
                        <c:choose>
                            <c:when test="${requestScope.page eq i}">
                                <li><a class="page-link">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=find_users&page=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                        <li><a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=find_users&page=${requestScope.page+1}">
                            <span aria-hidden="true">&raquo;</span></a></li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
    <c:if test="${fn:length(users) == 0}">
        <p class="info-style">${warningMessageUserSearch} <span class="info-style-srh">"${searchQuery}"</span></p>
    </c:if>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
