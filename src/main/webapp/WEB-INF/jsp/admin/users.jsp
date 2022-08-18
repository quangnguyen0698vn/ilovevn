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
</head>

<body class="d-flex flex-column min-vh-100">
<span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
<%--<span id="contextPath2" style="display:none;"><c:out value="${pageContext.request.requestURI}"></c:out></span>--%>

<%-- Navbar--%>
<jsp:include page="fragments/header.jsp"/>

<%--<div class="container">--%>
<%--  <table id="table_many_ajax_calls" class="display d-none">--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--      <th>Mã số</th>--%>
<%--      <th>Họ và tên</th>--%>
<%--      <th>Email</th>--%>
<%--      <th>Địa chỉ</th>--%>
<%--      <th>Số điện thoại</th>--%>
<%--      <th>Vai trò</th>--%>
<%--      <th>Avatar</th>--%>
<%--    </tr>--%>
<%--  </table>--%>
<%--</div>--%>

<div style="width: 95%;" class="mx-auto">

  <c:forEach items="${message}" varStatus="status">
    <h4 class="mb-1
      <c:if test="${success[status.index]}">text-success</c:if>
      <c:if test="${!success[status.index]}">text-danger</c:if>
    ">
    <c:out value="${message[status.index]}"></c:out>
    </h4>
  </c:forEach>

  <table id="table_less_ajax_calls" class="display mt-5 table table-bordered table-striped table-hover table-responsive-xl">
    <thead class="bg-dark text-white">
    <tr>
      <th>Mã số</th>
      <th>Họ và tên</th>
      <th>Email</th>
      <th>Địa chỉ</th>
      <th>Số điện thoại</th>
      <th>Vai trò</th>
      <th>Avatar</th>
      <th>Enabled</th>
      <th></th>
    </tr>
  </table>
</div>


<%--Footer--%>
<jsp:include page="fragments/footer.jsp"/>


<%-- js framework --%>
<jsp:include page="../common/jsFramework.jsp"/>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs5/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.js"></script>


<script type="text/javascript" src="<c:url value="/js/user_table_render.js" context="${pageContext.request.contextPath}"></c:url>"></script>
<script>

</script>
</body>
</html>