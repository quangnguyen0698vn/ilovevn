<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

  <section class="container mb-5" style="max-width: 68rem">
    <c:if test="${success != null}">
      <div class="row">
        <h4 class="${success ? "text-success" : "text-danger"}">
          <c:out value="${message}"></c:out>
        </h4>
      </div>
    </c:if>
    <div class="row m-2 d-flex flex-wrap align-items-center">
      <div class="col-md-8 col-12">
        <h1 class="text-success fs-3 mt-5">Nền tảng quyên góp từ thiện I love VN</h1>
        <p class="mt-4">
          I love VN là nền tảng giúp bạn dễ dàng chung tay quyên góp tiền cùng hàng triệu người, giúp đỡ các hoàn cảnh khó khăn trên khắp cả nước.
        </p>

          <div class="row">
            <div class="col-md-3 col-4">
              <h4 class="align-middle">| <span class="align-middle">322</span></h4>
              <p>dự án đã được gây quỹ thành công</p>
            </div>
            <div class="col-md-3 col-4">
              <h4 class="align-middle">| <span class="align-middle">48</span><span class="align-bottom">+ tỷ</span></h4>
              <p>đồng được quyên góp</p>
            </div>
            <div class="col-md-3 col-4">
              <h4 class="align-middle">| <span class="align-middle">12</span><span class="align-bottom">+ triệu</span></h4>
              <p>lượt quyên góp</p>
            </div>
            <div class="col-md-3 d-none d-md-block">

            </div>
          </div>


        <div class="mt-3">
          <a href="#projectSection" id="btnProjects" class="btn btn-success p-2 align-top">
            Quyên góp
          </a>

          <a href="#charitySection" id="btnCharities" class="ms-4 btn btn-outline-success p-2 align-top">
            Các đối tác đồng hành
          </a>
        </div>


      </div>
      <div class="col-md-4 col-12 d-none d-md-block align-middle">
        <img class="img-fluid" src="${pageContext.request.contextPath}/images/ilovevn-cover.jpeg" />
      </div>
    </div>
  </section>


  <%--  Các hoàn cảnh quyên góp  --%>
  <section id="projectSection" class="container mb-5" style="max-width: 68rem">
    <div class="text-center">
      <h3 class="text-success fs-4">Các hoàn cảnh quyên góp</h3>
    </div>

    <div class="row project-container m-2 g-4">
    </div>
    <div class="text-primary mt-5 text-center">
      <button id="loadMoreProjectBtn" class="btn btn-outline-success p-2 align-top">
        Xem thêm &darr;
      </button>
    </div>
  </section>

  <section id="charitySection" class="container mb-5" style="max-width: 68rem">
    <div class="text-center">
      <h3 class="text-success fs-4">Các đối tác đồng hành</h3>
    </div>

    <div class="row charity-container m-2 g-4">
    </div>
    <div class="text-primary mt-5 text-center">
      <button id="loadMoreCharityBtn" class="btn btn-outline-success p-2 align-top">
        Xem thêm &darr;
      </button>
    </div>
  </section>

  <jsp:include page="footer.jsp"/>

  <jsp:include page="common/jsFramework.jsp"/>x
  <script src="js/homepage_render.js">
  </script>

  </body>

  </html>