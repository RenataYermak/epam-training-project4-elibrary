<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">

    <link href="${pageContext.request.contextPath}style.css" rel="stylesheet">
    <title><fmt:message key="error500.page"/></title>
</head>
<body>
<span style="color: black; ">
<hr/>
     <h1 align="center"><fmt:message key="error500.page"/></h1><br/><br/>
    <h1 align="center"><fmt:message key="error500.message"/></h1><br/><br/>
<h3 align="center"><a href="${pageContext.request.contextPath}/controller?command=find_books"><fmt:message
        key="error.backHome"/></a></h3>
<hr/>
</span>
</body>
</html>
