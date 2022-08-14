<%--
  Created by IntelliJ IDEA.
  User: quang
  Date: 8/12/22
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <jsp:include page="common/cssFramework.jsp"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.css">
    <link rel="stylesheet" href="css/table.css"/>
</head>
<body>

    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

    <div class="container">
        <h1>Hello World</h1>
    </div>

    <div style="width: 90%" class="mx-auto">
        <table id="table-main" class="table table-bordered table-striped table-hover table-responsive-xl"
               data-toggle="table"
               data-url="http://localhost:8585/ilovevn/rest/projects"
               data-search="true"
               data-pagination="true"
               data-page-size="5"
               data-page-list="[5, 10, 15, All]"
        >
            <thead>
                <tr>
                    <th data-sortable="true" data-field="id">Mã số dự án</th>
                    <th data-sortable="true" data-field="name">Tên dự án</th>
                    <th data-field="imagePath" data-formatter="imageFormatter">Hình ảnh</th>
                    <th data-sortable="true" data-field="startedDate">Ngày bắt đầu</th>
                    <th data-sortable="true" data-field="expiredDate">Ngày kết thúc</th>
                    <th data-sortable="true" data-field="raisedAmount">Số tiền quyên góp được</th>
                    <th data-sortable="true" data-field="targetAmount">Số tiền dự kiến quyên góp</th>
                    <th data-sortable="true" data-field="raisedPercentage">Tiến độ</th>
                    <th data-sortable="true" data-field="charity.name">Tổ chức từ thiện</th>
                </tr>
            </thead>
        </table>
    </div>

    <div class="container">
        <h1>Hello World</h1>
    </div>



    <jsp:include page="common/jsFramework.jsp" />
    <script src="https://unpkg.com/bootstrap-table@1.20.2/dist/bootstrap-table.min.js"></script>
    <script>
        const prefix = $("#contextPath")[0].outerText;
        console.log(prefix);
        function imageFormatter(value) {
            return `<img src="` + prefix + value + `" class="img-fluid"/>`;
        }
    </script>
</body>
</html>
