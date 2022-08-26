<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>I love VN - Hệ thống quyên góp từ thiện</title>
    <jsp:include page="common/cssFramework.jsp"/>

    <link rel="stylesheet" href="css/general.css"/>
    <link rel="stylesheet" href="css/style.css" />
  </head>

  <body class="d-flex flex-column min-vh-100">

  <jsp:include page="header.jsp"/>
  <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

  <c:if test="${success != null}">
  <section class="container mb-5" style="max-width: 68rem">
    <div class="row">
      <h4 class="${success ? "text-success" : "text-danger"}">
        <c:out value="${message}"></c:out>
      </h4>
    </div>
  </section>
  </c:if>

<%--  <jsp:include page="fragments/here_section.jsp"/>--%>

  <section id="charitySection" class="container mb-5" style="max-width: 68rem">
    <div>
      <h3 class="text-success fs-3">Các đối tác đồng hành</h3>
    </div>

    <jsp:include page="fragments/charity_container.jsp"/>
  </section>

  <section id="projectSection" class="container mb-5" style="max-width: 68rem">
    <div class="text-center">
      <h3 class="text-success fs-4">Các hoàn cảnh quyên góp</h3>
    </div>

    <jsp:include page="fragments/project_container.jsp"/>
  </section>



  <jsp:include page="fragments/donateModal.jsp"/>

  <jsp:include page="footer.jsp"/>

  <jsp:include page="common/jsFramework.jsp"/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

  <script src="js/homepage_render.js">
  </script>



  </body>

  </html>