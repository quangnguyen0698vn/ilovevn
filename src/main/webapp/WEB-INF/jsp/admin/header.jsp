<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="bg-dark mb-5">
  <nav class="container navbar navbar-expand-lg bg-dark navbar-dark">
    <a href="#" class="navbar-brand">
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
      <ul class="navbar-nav">
        <li class="nav-item p-1">
          <a href="../index.html" class="nav-link">Trang chủ</a>
        </li>

        <li class="nav-item p-1">
          <a href="index.html" class="nav-link">Admin Dashboard</a>
        </li>

        <li class="nav-item p-1">
          <a href="project.html" class="nav-link active">Dự án Quyên Góp</a>
        </li>

        <li class="nav-item p-1">
          <a href="donation.html" class="nav-link">Danh sách quyên góp</a>
        </li>

        <li class="nav-item p-1">
          <a href="user.html" class="nav-link">Người dùng</a>
        </li>

        <li class="nav-item p-1">
          <a href="charity.html" class="nav-link">Các tổ chức từ thiện</a>
        </li>

        <li class="nav-item p-2 text-muted">
          <span><i class="fa-solid fa-user fa-2x px-1"></i></span>
          <span class="text-light">Nguyễn Ngọc Quang</span>
        </li>

        <li class="nav-item p-2">
          <a href="#" class="btn btn-warning">Log out</a>
        </li>
      </ul>
    </div>
  </nav>
</header>
