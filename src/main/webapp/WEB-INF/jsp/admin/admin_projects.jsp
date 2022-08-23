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

<jsp:include page="fragments/project_filter_and_search.jsp"/>

<!-- List all Projects -->
<c:if test="${totalPages == 0}">
<section class="container mt-5">
    <h4 class="text-danger">Không có kết quả nào!</h4>
    <h4 class="text-muted">Xin kiểm tra lại các điều kiện lọc, tìm kiếm</h4>
</section>
</c:if>
<c:if test="${totalPages > 0}">
    <section class="mx-auto mt-5" style="width: 90%;">

        <jsp:include page="fragments/project_full_details.jsp"/>

        <jsp:include page="fragments/project_less_details.jsp"/>

        <jsp:include page="fragments/project_pagination.jsp"/>

    </section>
</c:if>


<%--Footer--%>
<jsp:include page="admin_footer.jsp"/>

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
            let link = "<c:url value="/admin/projects/delete/admin_projects.jsp" context="${pageContext.request.contextPath}"></c:url>" + id;
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

