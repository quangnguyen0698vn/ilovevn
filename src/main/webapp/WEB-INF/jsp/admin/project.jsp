<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>I ❤ VN</title>
    <!-- Bootstrap and Fontawesome -->
    <jsp:include page='../common/cssFramework.jsp'/>
    <!-- My Own style -->
    <link rel="stylesheet" href="<c:url value="/css/style.css" context="${pageContext.request.contextPath}"></c:url>" />
</head>

<body class="d-flex flex-column min-vh-100">
<%-- Navbar--%>
<jsp:include page="header.jsp"/>

<section class="container">
    <h2>Quản lý các dự án quyên góp từ thiện</h2>
    <a href="
       ${pageContext.request.contextPath}/admin/project/create
    " class="btn btn-primary">Thêm Dự Án</a>

    <!-- Fiter and Search -->
    <form action="#" id="searchForm" class="row mt-2">
        <div class="col-md-4 col-12">
            <label for="dropdownCharity">Tổ chức:</label>
            <select class="form-control" name="charityId" id="dropdownCharity">
                <option value="0">Tất cả tổ chức từ thiện</option>
                <c:forEach items="${charities}" var="charity" varStatus="status">
                    <option value="<c:out value="${charity.id}"></c:out>">
                        <c:out value="${charity.name}"></c:out>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4 col-12">
            <label for="keyword">Từ khóa:</label>
            <input
                    type="search"
                    name="keyword"
                    value=""
                    class="form-control"
                    required=""
                    id="keyword"
            />
        </div>

        <div class="col-md-4 col-12 mt-auto">
            <button type="submit" class="btn btn-primary px-1 mx-2 mt-2">
                <i class="fas fa-search fa-2x"></i>
            </button>

            <button
                    type="button"
                    class="btn btn-secondary px-1 mx-2 mt-2"
                    onclick="window.location.href=window.location.href"
            >
                <i class="fas fa-eraser fa-2x"></i>
            </button>
        </div>
    </form>
</section>

<!-- List all Projects -->
<section class="container-xl mt-5">
    <div class="full-details">
        <table
                class="table table-bordered table-striped table-hover table-responsive-xl"
        >
            <thead class="bg-dark text-white">
            <tr>
                <th class="hideable-column">
                    Mã số
                </th>

                <th>
                    Tên dự án
                </th>

                <th class="hideable-column">Hình ảnh</th>
                <th>Số tiền quyên góp được</th>
                <th>Số tiền dự kiến quyên góp</th>
                <th>Tiến độ</th>
                <th class="hideable-column">Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>

                <th class="hideable-column">Tổ chức từ thiện</th>

                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${projects}" var="project" varStatus="status">
                <tr>
                    <td class="hideable-column"><c:out value="${project.id}"></c:out></td>
                    <td>
                        <c:out value="${project.name}"></c:out>
                    </td>
                    <td class="hideable-column">
                        <img
                                src="<c:url value="${project.getImagePath()}"></c:url>"
                                style="width: 500px"
                                class="img-fluid"
                        />
                    </td>

                    <fmt:setLocale value="vn_VN"/>
                    <td><fmt:formatNumber value="${raisedAmounts[status.index]}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                    <td><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                    <td><fmt:formatNumber value="${raisedAmounts[status.index] / project.targetAmount}" type="percent" minFractionDigits="2"/></td>
                    <td class="hideable-column"><fmt:formatDate value="${project.startedDate}" pattern="dd-MM-yyyy" /></td>
                    <td><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></td>

                    <td class="hideable-column"><c:out value="${project.charity.name}"></c:out></td>

                    <td>
                        <div class="mb-1">
                            <a
                                    class="fas fa-edit fa-2x icon-green"
                                    href="#"
                                    title="Sửa thông tin dự án"
                            ></a>
                        </div>

                        <div>
                            <a class="fas fa-trash fa-2x icon-dark link-delete" href="#" title="Xóa dự án này"></a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="less-details">
        <div class="row m-1">
            <c:forEach items="${projects}" var="project" varStatus="status">

                <div class="card mb-3">

                    <img src="<c:url value="${project.getImagePath()}"></c:url>" class="card-img-top img-fluid" alt="Hình minh họa" />
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${project.name}"></c:out></h5>

                        <p class="card-text">Số tiền quyên góp được <span><fmt:formatNumber value="${raisedAmounts[status.index]}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>

                        <p class="card-text">Số tiền dự kiến quyên góp <span><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>

                        <p class="card-text">Ngày hết hạn <span><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></span></p>
                    </div>
                    <div class="text-center">
                        <a
                                class="fas fa-edit fa-2x icon-green mb-3"
                                href="#"
                                title="Sửa thông tin dự án"
                        ></a>
                        &nbsp;
                        <a class="fas fa-trash fa-2x icon-dark mb-3" href="#" title="Xóa dự án này"></a>
                    </div>


                </div>
            </c:forEach>
        </div>
    </div>
</section>

<%--Footer--%>
<jsp:include page="footer.jsp"/>

<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>
</body>
</html>

