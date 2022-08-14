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

<div class="container">
  <table id="table_less_ajax_calls" class="display table table-bordered table-striped table-hover table-responsive-xl">
    <thead class="bg-dark text-white">
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
  // console.log(prefix);

  let debug = function(name, value) {
    // console.log(name);
    // console.dir(value);
  }


  $.fn.dataTable.pipeline = function (opts) {
    var conf = $.extend(
            {
              data: null, // function or object with parameters to send to the server
              // matching how `ajax.data` works in DataTables
              method: 'GET', // Ajax HTTP method
            },
            opts
    );

    debug('conf', conf);

    // Private variables for storing the cache
    var cacheLower = -1;
    var cacheUpper = null;
    var cacheLastRequest = null;
    var cacheLastJson = null;

    return function (request, drawCallback, settings) {
      var ajax = false;
      var requestStart = request.start;
      var drawStart = request.start;
      var requestLength = request.length;
      var requestEnd = requestStart + requestLength;

      debug('request', request);
      // debug('drawCallback', drawCallback);
      // debug('settings', settings);
      debug('requestStart', requestStart);
      debug('drawStart', drawStart);
      debug('requestLength', requestLength);
      debug('requestEnd', requestEnd);

      if (settings.clearCache) {
        // API requested that the cache be cleared
        ajax = true;
        settings.clearCache = false;
      } else if (cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper) {
        // outside cached data - need to make a request
        ajax = true;
      } else if (
              JSON.stringify(request.order) !== JSON.stringify(cacheLastRequest.order) ||
              JSON.stringify(request.columns) !== JSON.stringify(cacheLastRequest.columns) ||
              JSON.stringify(request.search) !== JSON.stringify(cacheLastRequest.search)
      ) {
        // properties changed (ordering, columns, searching)
        ajax = true;
      }

      // Store the request for checking next time around
      cacheLastRequest = $.extend(true, {}, request);

      if (ajax) {
        // Need data from the server
        if (requestStart < cacheLower) {
          requestStart = requestStart - requestLength * (conf.pages - 1);

          if (requestStart < 0) {
            requestStart = 0;
          }
        }

        cacheLower = requestStart;
        cacheUpper = requestStart + requestLength * conf.pages;

        request.start = requestStart;
        request.length = requestLength * conf.pages;

        $.extend(request, conf.data);
        debug('request at line 146: ', request);

        // Quang's ajax call:
        return $.ajax({
          type: conf.method,
          url: conf.url,
          data: {
            pageSize: request.length,
            pageNum: (request.length + request.start) / request.length,
            sortField: request.columns[request.order[0].column].data,
            sortDir: request.order[0].dir,
            keyword: request.search.value
          },
          dataType: 'json',
          success: function(json) {
            json = {
              recordsTotal: json.totalUsers,
              recordsFiltered: json.totalUsers,
              data: json.data,
              draw: request.draw,
            }
            debug('json at line 168: ', json);

            cacheLastJson = $.extend(true, {}, json);
            debug('cacheLastJson at line 171: ', cacheLastJson);

            if (cacheLower != drawStart) {
              json.data.splice(0, drawStart - cacheLower);
              debug('json at line 175: ', json);
            }
            if (requestLength >= -1) {
              json.data.splice(requestLength, json.data.length);
              debug('json at line 179: ', json);
            }

            drawCallback(json);
          }
        });
      } else {
        // do not need to call ajax
        json = $.extend(true, {}, cacheLastJson);
        json.draw = request.draw; // Update the echo for each response
        json.data.splice(0, requestStart - cacheLower);
        json.data.splice(requestLength, json.data.length);

        drawCallback(json);
      }
    };
  };
  // Register an API method that will empty the pipelined data, forcing an Ajax
  // fetch on the next draw (i.e. `table.clearPipeline().draw()`)
  $.fn.dataTable.Api.register('clearPipeline()', function () {
    return this.iterator('table', function (settings) {
      settings.clearCache = true;
    });
  });

  $(document).ready( function() {
    $("#table_less_ajax_calls").DataTable({
      process: true,
      search: {
        return: true, //false ~ instant search ~ more ajaxs than true
      },
      serverSide: true,
      lengthMenu: [
        [3, 6, 9, -1],
        [3, 6, 9, 'All'],
      ],
      ajax:
        $.fn.dataTable.pipeline({
          url: 'http://localhost:8585/ilovevn/api/users',
          pages: 5, // number of pages to cache
        }),
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
      } ],

      language: {
        url: 'https://cdn.datatables.net/plug-ins/1.12.1/i18n/vi.json'
      }
    });
  });

  // $(document).ready( function () {
  //   $('#table_many_ajax_calls').DataTable({
  //     processing: true,
  //     search: {
  //       return: true,
  //     },
  //     serverSide: true,
  //     lengthMenu: [
  //       [3, 6, 9, -1],
  //       [3, 6, 9, 'All'],
  //     ],
  //     ajax: function(data, callback) {
  //       // data.length = 3;
  //       // console.dir(data);
  //       // console.dir(data.columns[data.order[0].column].data);
  //       // console.dir(data.order[0].dir);
  //       $.get('http://localhost:8585/ilovevn/api/users', {
  //         pageSize: data.length,
  //         pageNum: (data.length + data.start) / data.length,
  //         sortField: data.columns[data.order[0].column].data,
  //         sortDir: data.order[0].dir,
  //         keyword: data.search.value
  //       } , function(res) {
  //         callback({
  //           recordsTotal: res.totalUsers,
  //           recordsFiltered: res.totalUsers,
  //           data: res.data,
  //           draw: data.draw,
  //         });
  //       });
  //     },
  //     columns: [
  //
  //       // { title: 'Mã số'},
  //       {data: 'id'},
  //       // { title: 'Họ và tên'},
  //       {data: 'fullName'},
  //       // { title: 'Email'},
  //       {data: 'email'},
  //       // { title: 'Địa chỉ'},
  //       {data: 'address'},
  //       // { title: 'Số điện thoại'},
  //       {data: 'phoneNumber'},
  //       // { title: 'Vai trò'},
  //       {data: 'role.name'},
  //       // { title: 'Avatar'},
  //       {data: 'profilePhotoPath'},
  //     ],
  //
  //     columnDefs: [ {
  //       targets: 6,
  //       "render": function ( data, type, row, meta ) {
  //         const imgSource = prefix + '/' + data;
  //         return '<img class="img-fluid" src="' + imgSource + '"/>';
  //       }
  //     } ],
  //   });
  // } );
</script>

</body>
</html>