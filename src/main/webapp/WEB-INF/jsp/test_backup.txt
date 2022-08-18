<%--
  Created by IntelliJ IDEA.
  User: quang
  Date: 8/12/22
  Time: 3:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link href="https://unpkg.com/gridjs/dist/theme/mermaid.min.css" rel="stylesheet" />
    <jsp:include page="common/cssFramework.jsp"/>
</head>
<body>
    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>


    <div id="table"></div>

    <jsp:include page="common/jsFramework.jsp"/>

    <script type="module">
        import {
            Grid,
            html
        } from "https://unpkg.com/gridjs?module";

        const prefix = $("#contextPath")[0].outerText;
       // console.log($("#contextPath")[0].outerText);

        function createImageElement(imagePath) {
            const image = document.createElement('img');
            image.setAttribute('src', imagePath);
            return image;
        }

        const grid = new Grid({
            columns: ['Id', 'Name', 'img'],
            server: {
                url: 'http://localhost:8585/ilovevn/rest/projects',
                then: data => data.map(project => [project.id, project.name, createImageElement(prefix + project.imagePath)])
            }
        }).updateConfig({
            resizable: true,
            sort: true,
            className: {
                container: 'container',
                table: "table table-striped table-hover table-bordered"
            }
        }).render(document.getElementById("table"));

        grid.on('ready', () => {
           $("img").each(function(index, element) {
              $(element).addClass('img-fluid');
           });
        });


    </script>
</body>
</html>
