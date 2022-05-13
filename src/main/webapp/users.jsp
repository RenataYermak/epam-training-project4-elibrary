<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
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
<%@ include file="/header.jsp" %>
<%@ include file="/sidebar.jsp" %>
<div id="content">
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="page.title.users"/></h2>
        </div>
        <div class="content-search">
            <form action="controller" method="get" name="searchForm">
                <label for="site-search">
                    <input type="search" name="searchQuery" id="site-search" placeholder=<fmt:message key="users.label.user_search"/>
                            required pattern="^[\p{L}\d-.]{2,25}$">
                </label>
                <button type="submit" name="command" value="user_search"><fmt:message key="users.button.search"/></button>
            </form>
        </div>
    </div>
    <hr/>
    <c:if test="${fn:length(users) > 0}">
        <table class="table">
            <tr class="row header green">
                <th class="cell"><fmt:message key="users.table.label.login"/></th>
                <th class="cell"><fmt:message key="users.table.label.first_name"/></th>
                <th class="cell"><fmt:message key="users.table.label.second_name"/></th>
                <th class="cell"><fmt:message key="users.table.label.email"/></th>
                <th class="cell"><fmt:message key="users.table.label.role"/></th>
                <th class="cell"><fmt:message key="users.table.label.status"/></th>
                <th class="cell"><fmt:message key="users.table.label.activation_date"/></th>
                <th class="cell"><fmt:message key="users.table.label.deactivation_date"/></th>
                <th class="cell"><fmt:message key="users.table.label.action"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr class="row">
                    <td class="cell">${user.login}</td>
                    <td class="cell">${user.firstName}</td>
                    <td class="cell">${user.secondName}</td>
                    <td class="cell">${user.email}</td>
                    <td class="cell">${user.role.name}</td>
                    <td class="cell">${user.status.name}</td>
                    <td class="cell">${user.activationDate}</td>
                    <td class="cell">${user.deactivationDate}</td>
                    <td class="cell">
                        <c:if test="${sessionScope.authUser.role == 'ADMIN' ||
                                    sessionScope.authUser.login == user.login}">
                            <form action="controller">
                                <label>
                                    <input hidden name="userId" value="${user.id}">
                                </label>
                                <button class="actionButton" type="submit" name="command" value="find_user">
                                    <fmt:message key="users.button.edit"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${fn:length(users) == 0}">
        <p class="info-style">${warningMessageUserSearch} <span class="info-style-srh">"${searchQuery}"</span></p>
    </c:if>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>
