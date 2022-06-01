<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="crt" uri="/WEB-INF/tld/copyright.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>Books Orders</title>
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
    <div class="content-main">
        <c:if test="${ordersPageTitle != null}">
            <div class="content-title">
                <h2>${ordersPageTitle}</h2>
            </div>
        </c:if>
        <div class="content-search">
            <form action="controller" method="get" name="searchForm">
                <label for="site-search">
                    <input type="search" name="searchQuery" id="site-search" placeholder=
                    <fmt:message key="table.label.book_search"/>
                            required pattern="^[\p{L}\d-.]{2,25}$">
                </label>
                <button type="submit" name="command" value="book_search"><fmt:message
                        key="orders.button.search"/></button>
            </form>
        </div>
    </div>
    <hr/>
    <c:if test="${fn:length(orders) > 0}">
        <table class="table">
            <tr class="row header green">
                <th class="cell"><fmt:message key="book.label.title"/></th>
                <th class="cell"><fmt:message key="book.label.author"/></th>
                <th class="cell"><fmt:message key="book.label.category"/></th>
                <th class="cell"><fmt:message key="orders.label.type"/></th>
                <th class="cell"><fmt:message key="orders.label.status"/></th>
                <th class="cell"><fmt:message key="orders.label.count_book"/></th>
                <c:if test="${orderStatus == 'ordered'}">
                    <th class="cell"><fmt:message key="orders.label.ordered_date"/></th>
                </c:if>
                <c:if test="${orderStatus == 'reserved'}">
                    <th class="cell"><fmt:message key="orders.label.reserved_date"/></th>
                </c:if>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th class="cell"><fmt:message key="orders.label.action"/></th>
                </c:if>
            </tr>
            <c:forEach items="${orders}$" var="order">
                <tr class="row">
                    <td class="cell">${book.bookTitle}</td>
                    <td class="cell">${order.author}</td>
                    <td class="cell">${order.category}</td>
                    <td class="cell">${order.type.value}</td>
                    <td class="cell">${order.status.value}</td>
                    <td class="cell">${order.count}</td>
                    <c:if test="${orderStatus == 'ordered'}">
                        <td class="cell">${order.orderedDate}</td>
                    </c:if>
                    <c:if test="${orderStatus == 'reserved'}">
                        <td class="cell">${order.reservedDate}</td>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <td class="cell">
                            <c:if test="${orderStatus == 'ordered'}">
                                <form action="controller">
                                    <input hidden name="orderId" value="${order.id}">
                                    <button class="actionButton reserveButton" type="submit" name="command"
                                            value="reserve_book">
                                        <fmt:message key="orders.button.reserve"/>
                                    </button>
                                    <button class="actionButton rejectButton" type="submit" name="command"
                                            value="reject_order">
                                        <fmt:message key="orders.button.reject"/>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${orderStatus == 'reserved'}">
                                <form action="controller">
                                    <input hidden name="orderId" value="${order.id}">
                                    <button class="actionButton" type="submit" name="command" value="return_book">
                                        <fmt:message key="orders.button.return"/>
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${fn:length(orders) == 0}">
        <p class="info-style">There are no <span class="info-style-srh">${orderStatus}</span> books</p>
    </c:if>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>