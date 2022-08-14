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

</head>

<body class="d-flex flex-column min-vh-100">
<span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

<%-- Navbar--%>
<jsp:include page="fragments/header.jsp"/>

<div class="container">
  <table id="table_id" class="display">
    <thead>
    <tr>
      <th>Mã số</th>
      <th>Họ và tên</th>
      <th>Email</th>
      <th>Địa chỉ</th>
      <th>Số điện thoại</th>
      <th>Vai trò</th>
      <th>Avatar</th>
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


<script>
  const prefix = $("#contextPath")[0].outerText;
  console.log(prefix);
  $(document).ready( function () {
    $('#table_id').DataTable({
      processing: true,
      serverSide: true,
      lengthMenu: [
        [3, 6, 9, -1],
        [3, 6, 9, 'All'],
      ],
      ajax: function(data, callback) {
        // data.length = 3;
        console.dir(data);
        console.dir(data.columns[data.order[0].column].data);
        console.dir(data.order[0].dir);
        $.get('http://localhost:8585/ilovevn/api/users', {
          pageSize: data.length,
          pageNum: (data.length + data.start) / data.length,
          sortField: data.columns[data.order[0].column].data,
          sortDir: data.order[0].dir
        } , function(res) {
          callback({
            recordsTotal: res.totalUsers,
            recordsFiltered: res.totalUsers,
            data: res.data,
            draw: data.draw,
          });
        });
      },
      columns: [

        // { title: 'Mã số'},
        {data: 'id'},
        // { title: 'Họ và tên'},
        {data: 'fullName'},
        // { title: 'Email'},
        {data: 'email'},
        // { title: 'Địa chỉ'},
        {data: 'address'},
        // { title: 'Số điện thoại'},
        {data: 'phoneNumber'},
        // { title: 'Vai trò'},
        {data: 'role.name'},
        // { title: 'Avatar'},
        {data: 'profilePhotoPath'},
      ],

      columnDefs: [ {
        targets: 6,
        "render": function ( data, type, row, meta ) {
          const imgSource = prefix + '/' + data;
          return '<img class="img-fluid" src="' + imgSource + '"/>';
        }
      } ]
    });
  } );
</script>

</body>
</html>