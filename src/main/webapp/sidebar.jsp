<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sidebar>
    <div id="sidebar">
        <div>
            <ul>
                <li>
                    <i class='fas fa-user-friends'></i>
                    <a href="${pageContext.request.contextPath}/controller?command=find_users"><fmt:message
                            key="sidebar.link.users"/></a>
                </li>
                <li>
                    <i class='fas fa-book'></i>
                    <a href="${pageContext.request.contextPath}/controller?command=find_books"><fmt:message
                            key="sidebar.link.books"/></a>
                </li>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <li>
                        <i class='fas fa-stream'></i>
                        <a href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=ordered">
                            <fmt:message key="sidebar.link.all_order"/>
                        </a>
                    </li>
                    <li><i class='fas fa-book-open'></i>
                        <a href="${pageContext.request.contextPath}/controller?command=find_orders_by_status&orderStatus=reserved">
                            <fmt:message key="sidebar.link.all_reserved"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.authUser.role != 'ADMIN'}">
                    <li>
                        <i class='fas fa-stream'></i>
                        <a href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${authUser.id}&orderStatus=ordered">
                            <fmt:message key="sidebar.link.my_order"/>
                        </a>
                    </li>
                    <li><i class='fas fa-book-open'></i>
                        <a href="${pageContext.request.contextPath}/controller?command=find_orders_by_user&userId=${authUser.id}&orderStatus=reserved">
                            <fmt:message key="sidebar.link.my_reserved"/>
                        </a>
                    </li>
                </c:if>
                <hr/>
                <li>
                    <i class='fas fa-globe'></i>
                    <a href="b_author.html"><fmt:message key="sidebar.link.about_library"/></a>
                </li>
            </ul>
        </div>
    </div>
</sidebar>