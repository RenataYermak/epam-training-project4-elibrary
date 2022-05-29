<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <title>About eLibrary</title>
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
<%@ include file="/jsp/header.jsp" %>
<%@ include file="/jsp/sidebar.jsp" %>
<div id="content" style="width: 1000px; height: 610px;">
    <div class="content-main">
        <div class="content-title">
            <h2><fmt:message key="about.library.title"/></h2>
        </div>
    </div>
    <hr/>
    <div class="about_text_right" style="float: left; width: 400px; height: 205px; color:#282727">
        <p>
        <li><fmt:message key="about_library.info"/></li>
        <li><fmt:message key="about_library.info1"/></li>
        </p>
        <p>
        <li><h3><fmt:message key="about_library.contacts"/></h3></li>
        <li><fmt:message key="about_library.address"/></li>
        <li><fmt:message key="about_library.tel"/></li>
        </p>
        <p>
        <li><h3><fmt:message key="about_library.work.time1"/></h3></li>
        <li><fmt:message key="about_library.work.time2"/></li>
        <li><fmt:message key="about_library.work.time3"/></li>
        </p>
    </div>
    <div class="about_img_right" style="float: left;">
        <iframe src=https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d18449.272217502963!2d25.279918395446547!3d54.68922912611766!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dd943ded481a27%3A0xeb3feb35b536d011!2sLittle%20Free%20Library!5e0!3m2!1sru!2slt!4v1653648912167!5m2!1sru!2slt"
                width="560" height="405" style="border:0;margin-top: 10px;margin-left: 10px; margin-right: 10px"
                allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
    </div>
</div>
<%@ include file="/jsp/footer.jsp" %>
</body>
</html>
