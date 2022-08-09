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
<jsp:include page="header.jsp"/>
<section class="mx-auto" style="width: 85%;">
    <h2>Quản lý các dự án quyên góp từ thiện</h2>

    <c:if test="${message!=null}">
        <c:if test="${success == false}"><h4 class="text-danger my-3"><c:out value="${message}"></c:out></h4></c:if>
        <c:if test="${success == true}"><h4 class="text-success my-3"><c:out value="${message}"></c:out></h4></c:if>
    </c:if>

    <a href="
       ${pageContext.request.contextPath}/admin/projects/createForm
    " class="btn btn-primary m-3">Thêm Dự Án</a>

    <a href="#" id="bulk-selector-enable" class="btn btn-warning m-3">Chọn nhiều dự án</a>
    <a href="#" id="bulk-selector-disable" class="btn btn-warning m-3" hidden>Tắt chọn nhiều dự án</a>
    <button type="submit" id="bulk-delete-button" form="hidden-form" class="btn btn-danger" hidden>Xóa các projects đã chọn</button>




    <!-- Fiter and Search -->

    <h4 class="text-muted mt-5">Lọc và tìm kiếm dự án</h4>
    <form action="" id="searchForm" class="row mt-2">
        <div class="col-md-5 col-12">
            <label for="dropdownCharity">Tổ chức:</label>
            <select class="form-control" name="charityId" id="dropdownCharity">
                <option value="0">Tất cả tổ chức từ thiện</option>
                <c:forEach items="${charities}" var="charity">
                    <c:if test="${charity.id == selectedCharityId}">
                        <option value="<c:out value="${charity.id}"></c:out>" selected>
                            <c:out value="${charity.name}"></c:out>
                        </option>
                    </c:if>
                    <c:if test="${charity.id != selectedCharityId}">
                        <option value="<c:out value="${charity.id}"></c:out>">
                            <c:out value="${charity.name}"></c:out>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-5 col-12">
            <label for="keyword">Từ khóa:</label>
            <input
                    type="search"
                    name="keyword"
                    class="form-control"
                    id="keyword"
                    placeholder="Nhập từ khóa bạn muốn tìm kiếm"
                    value="<c:out value="${currentKeyword}" default=""></c:out>"
            />
        </div>


        <div class="col-md-5 col-12">
            <label for="sortField">Sắp xếp theo</label>
            <select class="form-control" name="sortField" id="sortField">
                <c:forEach items="${sortFieldValues}" varStatus="status">
                    <c:if test="${sortFieldValues[status.index] == sortField}">
                        <option selected value="${sortFieldValues[status.index]}">
                            <c:out value="${sortFieldNames[status.index]}"></c:out>
                        </option>
                    </c:if>
                    <c:if test="${sortFieldValues[status.index] != sortField}">
                        <option value="${sortFieldValues[status.index]}">
                            <c:out value="${sortFieldNames[status.index]}"></c:out>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-5 col-12">
            <label for="sortDir">Thứ tự</label>
            <select class="form-control" name="sortDir" id="sortDir">
                <c:forEach items="${sortDirValues}" varStatus="status">
                    <c:if test="${sortDirValues[status.index] == sortDir}">
                        <option selected value="${sortDirValues[status.index]}">
                            <c:out value="${sortDirNames[status.index]}"></c:out>
                        </option>
                    </c:if>
                    <c:if test="${sortDirValues[status.index] != sortDir}">
                        <option value="${sortDirValues[status.index]}">
                            <c:out value="${sortDirNames[status.index]}"></c:out>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </div>


        <div class="col-md-2 col-12">
            <button type="submit" class="btn btn-primary px-1 mx-2 mt-2">
                <i class="fas fa-search fa-2x"></i>
            </button>

            <button
                    type="button"
                    class="btn btn-secondary px-1 mx-2 mt-2"
                    onclick="window.location.href=window.location.origin+'/ilovevn/admin/projects/'"
            >
                <i class="fas fa-eraser fa-2x"></i>
            </button>
        </div>
    </form>
</section>

<!-- List all Projects -->
<c:if test="${totalPages == 0}">
<section class="container mt-5">
    <h4 class="text-danger">Không có kết quả nào!</h4>
    <h4 class="text-muted">Xin kiểm tra lại các điều kiện lọc, tìm kiếm</h4>
</section>
</c:if>
<c:if test="${totalPages > 0}">
    <section class="mx-auto mt-5" style="width: 90%;">

        <div class="full-details">
            <form id="hidden-form" action="<c:url value="/admin/projects/delete/" context="${pageContext.request.contextPath}"></c:url>">
                <table
                        class="table table-bordered table-striped table-hover table-responsive-xl"
                        data-toggle="table"
                        data-show-columns="true"
                >
                    <thead class="bg-dark text-white">
                    <tr>
                        <th class="checkbox-column"></th>
                        <th class="hideable-column" data-sortable="true" >
                            Mã số
                        </th>

                        <th data-sortable="true" >
                            Tên dự án
                        </th>

                        <th class="image-column" data-width="200" >Hình ảnh</th>
                        <th class="hideable-column" data-sortable="true" >Ngày bắt đầu</th>
                        <th data-sortable="true" >Ngày kết thúc</th>
                        <th data-sortable="true" >Số tiền quyên góp được</th>
                        <th data-sortable="true" >Số tiền dự kiến quyên góp</th>
                        <th data-sortable="true">Tiến độ</th>


                        <th class="hideable-column" data-sortable="true">Tổ chức từ thiện</th>

                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${projects}" var="project">
                        <tr>
                            <td class="checkbox-column">
                                <c:if test="${!project.isAlreadyStarted()}">
                                    <input type="checkbox" name="id" value="<c:out value="${project.id}"></c:out>"/>
                                </c:if>
                            </td>
                            <td class="hideable-column"><c:out value="${project.id}"></c:out></td>

                            <td>
                                <c:out value="${project.name}"></c:out>
                            </td>



                            <td class="image-column">
                                <img
                                        src="<c:url value="${project.getImagePath()}"></c:url>"
                                        style="width: 500px"
                                        class="img-fluid"
                                />
                            </td>

                            <td class="hideable-column"><fmt:formatDate value="${project.startedDate}" pattern="dd-MM-yyyy" /></td>
                            <td><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></td>

                            <fmt:setLocale value="vn_VN"/>
                            <td><fmt:formatNumber value="${project.raisedAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                            <td><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></td>
                            <td><fmt:formatNumber value="${project.raisedPercentage}" type="percent" minFractionDigits="2"/></td>
                            <td class="hideable-column"><c:out value="${project.charity.name}"></c:out></td>

                            <td>
                                <div class="mb-1">
                                    <a
                                            class="link-edit"
                                            href="<c:url value="/admin/projects/edit/${project.id}" context="${pageContext.request.contextPath}"></c:url>"
                                            title="Sửa thông tin dự án"
                                    >
                                        <i class="fas fa-edit fa-2x icon-green"></i>
                                    </a>
                                </div>

                                <c:if test="${!project.isAlreadyStarted()}">
<%--                                    <c:out value="${project.isAlreadyStarted()}"></c:out>--%>
                                    <div>
                                        <a class="link-delete"
                                           href="javascript:deleteProject(${project.id}, '${project.name}')"
                                           data-project-id = "<c:out value="${project.id}"></c:out>"
                                           title="Xóa dự án này">
                                            <i class="fas fa-trash fa-2x icon-dark"></i>
                                        </a>
                                    </div>
                                </c:if>




                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>

        </div>

        <div class="less-details">
            <div class="row m-1">
                <c:forEach items="${projects}" var="project" varStatus="status">

                    <div class="card mb-3">

                        <img src="<c:url value="${project.getImagePath()}"></c:url>" class="card-img-top img-fluid" alt="Hình minh họa" />
                        <div class="card-body">
                            <h5 class="card-title"><c:out value="${project.name}"></c:out></h5>
                            <p class="card-text">Số tiền quyên góp được <span><fmt:formatNumber value="${project.raisedAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>
                            <p class="card-text">Số tiền dự kiến quyên góp <span><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>
                            <p class="card-text">Ngày hết hạn <span><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></span></p>
                        </div>
                        <div class="text-center">
                            <a
                                    class="link-edit"
                                    href="<c:url value="/admin/projects/edit/${project.id}" context="${pageContext.request.contextPath}"></c:url>"
                                    title="Sửa thông tin dự án"
                            >
                                <i class="fas fa-edit fa-2x icon-green"></i>
                            </a>
                            &nbsp;
                            <c:if test="${!project.isAlreadyStarted()}">
                                <%--                                    <c:out value="${project.isAlreadyStarted()}"></c:out>--%>
                                    <a class="link-delete"
                                       href="javascript:deleteProject(${project.id}, '${project.name}')"
                                       data-project-id = "<c:out value="${project.id}"></c:out>"
                                       title="Xóa dự án này">
                                        <i class="fas fa-trash fa-2x icon-dark"></i>
                                    </a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- PAGINATION -->
        <c:set var="suffix" value="&charityId=${selectedCharityId}&keyword=${currentKeyword}&sortField=${sortField}&sortDir=${sortDir}"></c:set>
        <nav aria-label="Search results pages" class="row m-5">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage == 1}">
                    <li class="page-item disabled"><a href="#"
                                                      class="page-link">Previous</a></li>
                </c:if>

                <c:if test="${currentPage > 1}">
                    <li class="page-item"><a
                            href="?pageNum=${currentPage-1}${suffix}"
                            class="page-link">Previous</a></li>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}" step="1">
                    <c:if test="${i == currentPage}">
                        <li class="page-item active" aria-current="page"><a
                                href="?pageNum=${i}${suffix}" class="page-link">
                                ${i} <span class="visually-hidden">(current)</span>
                        </a></li>
                    </c:if>
                    <c:if test="${i != currentPage}">
                        <li class="page-item"><a
                                href="?pageNum=${i}${suffix}" class="page-link">${i}</a></li>
                    </c:if>
                </c:forEach>

                <c:if test="${currentPage == totalPages}">
                    <li class="page-item disabled"><a href="#"
                                                      class="page-link">Next</a></li>
                </c:if>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item"><a
                            href="?pageNum=${currentPage+1}${suffix}"
                            class="page-link">Next</a></li>
                </c:if>
            </ul>
        </nav>

    </section>
</c:if>


<%--Footer--%>
<jsp:include page="footer.jsp"/>

<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>

<%-- my own script --%>
<script>
    $("#dropdownCharity").on("change", function () {
        $("#searchForm").submit();
    })
</script>
<script src="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.js"></script>

<script>
    $("#bulk-selector-enable").click(function () {
        $("#bulk-selector-enable").attr("hidden", "true");
        $("#bulk-delete-button").removeAttr("hidden")
        $("#bulk-selector-disable").removeAttr("hidden")
        $(".checkbox-column").each(function() {
            $(this).css("display", "table-cell");
        })
    });

    $("#bulk-selector-disable").click(function() {
        $("#bulk-selector-enable").removeAttr("hidden");
        $("#bulk-delete-button").attr("hidden", "true");
        $("#bulk-selector-disable").attr("hidden", "true");
        $(".checkbox-column").each(function() {
            $(this).css("display", "none");
        })
    });

    function deleteProject(id, name) {
        console.log(id);
        if (confirm(`Bạn có muốn xóa dự án số ` + id + `: ` + name + ` không? Thao tác này sẽ không thể hoàn tác!`)) {
            let link = "<c:url value="/admin/projects/delete/" context="${pageContext.request.contextPath}"></c:url>" + id;
            // console.log(window.location.pathname);
            // console.log(link);
            // console.log(window.location.origin);
            window.location.assign(window.location.origin + link);
        }
    }

    function getToday() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        return yyyy+"-"+mm+"-"+dd;
    }
</script>
</body>
</html>

