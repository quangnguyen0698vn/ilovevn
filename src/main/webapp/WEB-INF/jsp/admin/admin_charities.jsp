<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>I ❤ VN</title>
    <!-- Bootstrap and Fontawesome -->
    <jsp:include page='../common/cssFramework.jsp'/>
    <!-- Bootstrap Table -->
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.css">
    <!-- My Own style -->
    <link rel="stylesheet" href="<c:url value="/css/style.css" context="${pageContext.request.contextPath}"></c:url>" />

</head>

<body class="d-flex flex-column min-vh-100">
<%-- Navbar--%>
<jsp:include page="admin_header.jsp"/>
<h1>Trang này đang được xây dựng</h1>

<jsp:include page="admin_footer.jsp"/>
<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>
</body>
