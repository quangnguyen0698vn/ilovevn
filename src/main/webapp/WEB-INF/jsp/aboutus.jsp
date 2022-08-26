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



  <section class="container mb-5" style="max-width: 68rem">
    <div class="row m-2 d-flex flex-wrap align-items-center">
      <div class="col-md-8 col-12">
        <h1 class="text-success fs-3 mt-5">Nền tảng quyên góp từ thiện I love VN</h1>
        <p class="mt-4">
          I love VN là nền tảng giúp bạn dễ dàng chung tay quyên góp tiền cùng hàng triệu người, giúp đỡ các hoàn cảnh khó khăn trên khắp cả nước.
        </p>

        <div class="row">
          <div class="col-md-3 col-4">
            <h4 class="align-middle">| <span class="align-middle number-of-projects"></span></h4>
            <p>dự án đã và đang được quyên góp</p>
          </div>
          <div class="col-md-3 col-4">
            <h4 class="align-middle">| <span class="align-middle sum-of-raised-amount">48</span><span class="align-bottom">+ triệu</span></h4>
            <p>đồng được quyên góp</p>
          </div>
          <div class="col-md-3 col-4">
            <h4 class="align-middle">| <span class="align-middle number-of-donations"></span><span class="align-bottom">+ </span></h4>
            <p>lượt quyên góp</p>
          </div>
          <div class="col-md-3 d-none d-md-block">
          </div>
        </div>


      </div>
      <div class="col-md-4 col-12 d-none d-md-block align-middle">
        <img class="img-fluid" src="${pageContext.request.contextPath}/images/ilovevn-cover.jpeg" />
      </div>
    </div>
  </section>

  <section id="aboutSection" class="container mb-5" style="max-width: 68rem">
    <div class="row m-2">
      <h3 class="text-success fs-2">Thông tin liên hệ</h3>
      <p>Mọi chi tiết xin liên hệ: </p>
      <ul class="ms-4">
        <li>Admin dự án: <span>Nguyễn Ngọc Quang</span></li>
        <li>Email: <span>quangnnfx16178@funix.edu.vn</span></li>
        <li>Văn phòng dự án: Ho Chi Minh city, Vietnam</li>
        <li>Điện Thoại: 19001000</li>
      </ul>
      <p>Xin chân thành cảm ơn.</p>
    </div>
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

  <script src="js/homepage_render.js">
  </script>



  </body>

  </html>