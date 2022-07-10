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
        <c:if test="${! empty ordersPageTitle}">
            <div class="content-title">
                <h2>${ordersPageTitle}</h2>
            </div>
        </c:if>
    </div>
    <hr/>

    <c:if test="${orders.size() > 0}">
        <table id="sorted-table" class="table">
            <thead>
            <tr class="row-header-green">
                <th class="cell-main"><fmt:message key="orders.label.title"/></th>
                <th class="cell-main"><fmt:message key="orders.label.user_name"/></th>
                <th class="cell-main"><fmt:message key="orders.label.type"/></th>
                <th class="cell-main"><fmt:message key="orders.label.status"/></th>
                <c:if test="${orderStatus == 'ordered'}">
                    <th class="cell-main"><fmt:message key="orders.label.ordered_date"/></th>
                </c:if>
                <c:if test="${orderStatus == 'reserved'}">
                    <th class="cell-main"><fmt:message key="orders.label.reserved_date"/></th>
                </c:if>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <th class="cell-main"><fmt:message key="orders.label.action"/></th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr class="row">
                    <td class="cell">${order.book.title}</td>
                        <%--                    <td class="cell">${order.book.author}</td>--%>
                    <td class="cell">${order.user.login}</td>
                    <td class="cell">${order.type.name}</td>
                    <td class="cell">${order.status.name}</td>
                    <c:if test="${orderStatus == 'ordered'}">
                        <fmt:parseDate value="${order.orderedDate}" pattern="y-M-dd'T'H:m"
                                       var="myParseDate"></fmt:parseDate>
                        <td class="cell"><fmt:formatDate value="${myParseDate}" pattern="HH:mm:ss dd.MM.yyyy"/></td>
                    </c:if>
                    <c:if test="${orderStatus == 'reserved'}">
                        <fmt:parseDate value="${order.reservedDate}" pattern="y-M-dd'T'H:m"
                                       var="myParseDate"></fmt:parseDate>
                        <td class="cell" style="color: #cb1313"><fmt:formatDate value="${myParseDate}"
                                                                                pattern="dd.MM.yyyy"/></td>
                    </c:if>
                    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                        <td class="cell">

                            <c:if test="${orderStatus == 'ordered'}">
                                <form action="controller">
                                    <input hidden name="orderId" value="${order.id}">
                                    <button class="actionButton reserveButton" style="margin: 0 10px 0 10%"
                                            type="submit" name="command"
                                            value="reserve_book">
                                        <fmt:message key="orders.button.reserve"/>
                                    </button>
                                    <button class="actionButton reserveButton"
                                            style="background-color: #f67f7f;width: 80px" type="submit" name="command"
                                            value="reject_order">
                                        <fmt:message key="orders.button.reject"/>
                                    </button>
                                </form>
                            </c:if>
                            <c:if test="${orderStatus == 'reserved'}">
                                <form action="controller">
                                    <input hidden name="orderId" value="${order.id}">
                                    <button class="actionButton " style="margin-left: 10%" type="submit" name="command"
                                            value="return_book">
                                        <fmt:message key="orders.button.return"/>
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
        <c:if test="${orderStatus == 'ordered'}">
            <div class="container">
                <div class="rows" style="justify-content: start">
                    <nav aria-label="pagination">
                        <ul class="pagination">
                            <c:if test="${requestScope.page  > 1}">
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=ordered&page=${requestScope.page-1}">
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
                                                   href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=ordered&page=${i}">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                                    <li><a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=ordered&page=${requestScope.page+1}">
                                        <span aria-hidden="true">&raquo;</span></a></li>
                                </c:if>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
        <c:if test="${orderStatus == 'reserved'}">
            <div class="container">
                <div class="rows" style="justify-content: start">
                    <nav aria-label="pagination">
                        <ul class="pagination">
                            <c:if test="${requestScope.page > 1}">
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=reserved&page=${requestScope.page-1}">
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
                                                   href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=reserved&page=${i}">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                                    <li><a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=reserved&page=${requestScope.page+1}">
                                        <span aria-hidden="true">&raquo;</span></a></li>
                                </c:if>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
    </c:if>
    <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
        <c:if test="${orderStatus == 'ordered'}">
            <div class="container">
                <div class="rows" style="justify-content: start">
                    <nav aria-label="pagination">
                        <ul class="pagination">
                            <c:if test="${requestScope.page > 1}">
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=ordered&page=${requestScope.page-1}">
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
                                                   href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=ordered&page=${i}">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                                    <li><a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=ordered&page=${requestScope.page+1}">
                                        <span aria-hidden="true">&raquo;</span></a></li>
                                </c:if>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
        <c:if test="${orderStatus == 'reserved'}">
            <div class="container">
                <div class="rows" style="justify-content: start">
                    <nav aria-label="pagination">
                        <ul class="pagination">
                            <c:if test="${requestScope.page > 1}">
                                <li><a class="page-link"
                                       href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=reserved&page=${requestScope.page-1}">
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
                                                   href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=reserved&page=${i}">${i}</a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${requestScope.page lt requestScope.number_of_pages}">
                                    <li><a class="page-link"
                                           href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${sessionScope.authUser.id}&orderStatus=reserved&page=${requestScope.page+1}">
                                        <span aria-hidden="true">&raquo;</span></a></li>
                                </c:if>
                            </c:if>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
    </c:if>
    <c:if test="${orders.size() == 0}">
        <p class="info-style">There are no <span class="info-style-srh">${orderStatus}</span> books</p>
    </c:if>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>