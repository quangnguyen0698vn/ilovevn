<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="theUri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<header class="mb-5" style="background-color: #e3f2fd;">
    <nav class="container navbar navbar-expand-lg navbar-light align-items-center" style="max-width: 68rem" style="background-color: #e3f2fd;">
        <a href="<c:url value="/" context="${pageContext.request.contextPath}"></c:url>" class="navbar-brand">
            <img src="<c:url value="/images/ilovevn-small.png" context="${pageContext.request.contextPath}"></c:url>" alt="Website's logo" />
        </a>

        <button
                class="navbar-toggler ms-auto"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarBlock"
                aria-controls="navbarBlock"
                aria-expanded="false"
                aria-label="Toggle navigation"
        >
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarBlock">
            <ul class="navbar-nav me-auto">
                <li class="nav-item p-1">
                    <a href="<c:url value="/" context="${pageContext.request.contextPath}"></c:url>" class="nav-link ${theUri.endsWith('/') ? 'active' : ''}">Trang chủ</a>
                </li>

                <sec:authorize access="hasRole('ADMIN')">
                <li class="nav-item p-1">
                    <a href="<c:url value="/admin/dashboard" context="${pageContext.request.contextPath}"></c:url>" class="nav-link ${theUri.endsWith('/admin/dashboard') ? 'active' : ''}">Admin Dashboard</a>
                </li>
                </sec:authorize>
                <li class="nav-item p-1">
                    <a href="<c:url value="/charity" context="${pageContext.request.contextPath}"></c:url>" class="nav-link ${theUri.endsWith('/charity') ? 'active' : ''}">Đối tác đồng hành</a>
                </li>

                <li class="nav-item p-1">
                    <a href="<c:url value="/aboutus" context="${pageContext.request.contextPath}"></c:url>" class="nav-link ${theUri.endsWith('/aboutus') ? 'active' : ''}">Về chúng tôi</a>
                </li>
            </ul>

            <ul class="navbar-nav ms-auto">
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item p-1">
                        <a href="<c:url value="/register" context="${pageContext.request.contextPath}"></c:url>" class="btn btn-outline-success btn-register">Đăng ký</a>
                    </li>
                    <li class="nav-item p-1">
                        <a href="<c:url value="/login" context="${pageContext.request.contextPath}"></c:url>" class="btn btn-outline-primary btn-login">Đăng nhập</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li class= "nav-item dropdown p-1">

                        <a type="button" class="dropdown-toggle text-success" id="dropdownButton" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="${pageContext.request.contextPath}<sec:authentication property="principal.profilePhotoPath" />" class="rounded-circle"
                                 height="50" alt="Avatar" loading="lazy" />
                        </a>

                        <sec:authentication property="principal.fullName"/>
<%--                        <sec:authentication property="principal.password"/>--%>
                        <div class="dropdown-menu" aria-labelledby="dropdownButton">
                            <a href="<c:url value="/changeUserInfo" context="${pageContext.request.contextPath}"></c:url>" class="dropdown-item">Thay đổi thông tin cá nhân</a>
                            <a href="<c:url value="/changePassword" context="${pageContext.request.contextPath}"></c:url>" class="dropdown-item">Đổi mật khẩu</a>
                            <a href="<c:url value="/viewDonateHistory" context="${pageContext.request.contextPath}"></c:url>" class="dropdown-item">Xem lịch sử quyên góp</a>
                            <form class="dropdown-item text-center" action="<c:url value="/logout" context="${pageContext.request.contextPath}"></c:url>" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-warning btn-small w-100">Thoát</button>
                            </form>

                        </div>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </nav>
</header>