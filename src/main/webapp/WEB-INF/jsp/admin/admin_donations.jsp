<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>I ❤ VN</title>
    <!-- Bootstrap and Fontawesome -->
    <jsp:include page='../common/cssFramework.jsp'/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs5/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.css"/>
    <style>
        td img {
            width: 90%;
        }
    </style>
    <link rel="stylesheet" href="<c:url value="/css/general.css" context="${pageContext.request.contextPath}"></c:url>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css" context="${pageContext.request.contextPath}"></c:url>"/>
</head>

<body class="d-flex flex-column min-vh-100">
<span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
<%--<span id="contextPath2" style="display:none;"><c:out value="${pageContext.request.requestURI}"></c:out></span>--%>

<%-- Navbar--%>
<jsp:include page="admin_header.jsp"/>

<div style="width: 95%;" class="mx-auto">

    <c:if test="${success0 != null}">
        <h4 class="mb-1
    <c:if test="${success0}">text-success</c:if>
    <c:if test="${!success0}">text-danger</c:if>
  ">
            <c:out value="${message0}"></c:out>
        </h4>
    </c:if>


    <h4 class="mb-1
      <c:if test="${success}">text-success</c:if>
      <c:if test="${!success}">text-danger</c:if>
    ">
        <c:out value="${message}"></c:out>
    </h4>

    <table id="donationTable"
           class="display mt-5 table table-bordered table-striped table-hover table-responsive-xl"
           style="width:100%"
    >
        <thead class="bg-dark text-white">
        <tr>
            <th>Mã số</th>
            <th>Ngày điền form</th>
            <th>Cập nhật lần cuối</th>
            <th>Họ và tên</th>
            <th>Số tiền</th>
            <th>Mã số giao dịch</th>
            <th>Lời nhắn</th>
            <th>Trạng thái</th>
            <th></th>
            <th>Mã số dự án</th>
            <th>Mã số người dùng</th>
        </tr>
    </table>
</div>


<%--Footer--%>
<jsp:include page="admin_footer.jsp"/>


<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs5/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-dateFormat/1.0/jquery.dateFormat.min.js" integrity="sha512-YKERjYviLQ2Pog20KZaG/TXt9OO0Xm5HE1m/OkAEBaKMcIbTH1AwHB4//r58kaUDh5b1BWwOZlnIeo0vOl1SEA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script type="text/javascript" src="<c:url value="/js/donation_table_render.js" context="${pageContext.request.contextPath}"></c:url>"></script>
<script>

</script>
</body>
</html>