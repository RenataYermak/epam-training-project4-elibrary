<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<header class="head">
    <div id="header">
        <div class="header-title">
            <h1 class="title"><span class="header-title-sp">e</span>Library</h1>
        </div>
        <c:if test="${sessionScope.authUser != null}">
            <div class="hrb-group">
                <div class="header-right-buttons">
                    <form action="controller">
                        <button class="btn" type="submit" name="command" value="sign_out">
                            <i class='fas fa-sign-out-alt'></i> <fmt:message key="header.log_out"/>
                        </button>
                    </form>
                </div>
                <div class="header-right-buttons">
                    <form action="controller">
                        <label>
                            <input hidden name="userId" value="${authUser.id}">
                        </label>
                        <button class="btn" type="submit" name="command" value="find_user">
                            <i class='fas fa-user-alt'></i> <fmt:message key="header.my_account"/>
                        </button>
                    </form>
                </div>
                <div class="header-right-buttons">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="change_locale">
                        <input type="hidden" name="currentPageAbsoluteURL" value="${pageContext.request.requestURL}">
                        <input type="hidden" name="currentParameters" value="${pageContext.request.getQueryString()}">
                        <button class="btn" type="submit" name="locale" value="ru_RU">
                            <fmt:message key="header.lang.ru"/>
                        </button>
                        <button class="btn" type="submit" name="locale" value="en_EN">
                            <fmt:message key="header.lang.en"/>
                        </button>
                    </form>
                </div>
<%--                <div class="header-right-buttons">--%>
<%--                    <div>--%>
<%--                        <p class="header-hello"><fmt:message key="header.hello"/> ${authUser.firstName}</p>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </div>
        </c:if>
    </div>
</header>

