<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${project.name}"></c:out></title>
    <jsp:include page="common/cssFramework.jsp"/>

    <link rel="stylesheet" href="<c:url value="/css/general.css" context="${pageContext.request.contextPath}"></c:url>"/>
    <link rel="stylesheet" href="<c:url value="/css/style.css" context="${pageContext.request.contextPath}"></c:url>"/>
  </head>

  <body class="d-flex flex-column min-vh-100">

  <jsp:include page="header.jsp"/>

  <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

  <section class="container mb-5 main-section" style="max-width: 68rem">
    <c:if test="${success != null}">
      <div class="row">
        <h4 class="${success ? "text-success" : "text-danger"}">
          <c:out value="${message}"></c:out>
        </h4>
      </div>
    </c:if>
    <c:if test="${project != null}">
    <div class="container">
      <h1 class="text-success fs-3"><c:out value="${project.name}"></c:out></h1>
      ❤<c:out value="${project.shortDescription}" escapeXml="false"></c:out>❤

      <div id="carouselControls" class="carousel slide m-4" data-bs-ride="carousel">
        <div class="carousel-inner container">
          <div class="carousel-item active">
            <img src="<c:url value="${project.imagePath}" context="${pageContext.request.contextPath}"></c:url>" alt="${project.name}" class="d-block w-100">
          </div>
          <c:forEach items="${project.images}" var="image">
            <div class="carousel-item">
              <img src="<c:url value="${image.imagePath}" context="${pageContext.request.contextPath}"></c:url>" alt="${image.fileName}" class="d-block w-100">
            </div>
          </c:forEach>
        </div>
        <button type="button" class="carousel-control-prev" data-bs-target="#carouselControls" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button type="button" class="carousel-control-next" data-bs-target="#carouselControls" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>


      <c:out value="${project.fullDescription}" escapeXml="false"></c:out>
    </div>
    </c:if>

  </section>


  <jsp:include page="footer.jsp"/>

  <jsp:include page="common/jsFramework.jsp"/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

  <script src="js/homepage_render.js">
  </script>



  </body>

  </html>