<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${charity.name}"></c:out></title>
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

    <c:if test="${charity != null}">

    <div class="container">

      <div class="text-center">

        <img src="<c:url value="${charity.charityLogoPath}" context="${pageContext.request.contextPath}"></c:url>" class="img-fluid mb-3" style="max-width: 300px; max-height: 300px"
             alt="Charity Logo" />

        <p class="text-muted"><c:out value="${charity.shortDescription}"></c:out>
      </div>

      <div class="container mt-3">
        <c:out value="${charity.fullDescription}" escapeXml="false"></c:out>
      </div>
    </div>
    </c:if>

  </section>

  <section id="charitySection" class="container mb-5" style="max-width: 68rem">
    <div class="text-center">
      <h3 class="text-success fs-4">Các đối tác đồng hành</h3>
    </div>

    <jsp:include page="fragments/charity_container.jsp"/>
  </section>

  <jsp:include page="footer.jsp"/>

  <jsp:include page="common/jsFramework.jsp"/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

  <script src="<c:url value="/js/homepage_render.js" context="${pageContext.request.contextPath}"></c:url>">
  </script>

  </body>

  </html>