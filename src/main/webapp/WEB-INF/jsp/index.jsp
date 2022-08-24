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
  <c:set var="theUri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
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
  <section class="container mb-5 <c:if test="${theUri.contains('charity')}">d-none</c:if>" style="max-width: 68rem">
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

  <div class="d-flex flex-column <c:if test="${theUri.contains('charity')}">flex-column-reverse</c:if>">
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
        <c:if test="${!theUri.contains('charity')}">
          <h3 class="text-success fs-4">Các đối tác đồng hành</h3>
        </c:if>
        <c:if test="${theUri.contains('charity')}">
          <h1 class="text-success fs-3 mt-5">Các đối tác đồng hành</h1>
        </c:if>
      </div>

      <div class="row charity-container m-2 g-4">
      </div>
      <div class="text-primary mt-5 text-center">
        <button id="loadMoreCharityBtn" class="btn btn-outline-success p-2 align-top">
          Xem thêm &darr;
        </button>
      </div>
    </section>
  </div>


  <button type="button" class="btn btn-primary d-none" data-bs-toggle="modal" data-bs-target="#modalHeaderFooter">Open modal</button>
  <div class="modal fade" id="modalHeaderFooter" tabindex="-1" aria-labelledby="modalHeaderFooterTitle" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalHeaderFooterTitle">Quyên góp cho dự án</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div>
            <sec:authorize access="!isAuthenticated()">
                <p><em>Để có thể lưu thông tin về <strong>lịch sử các lần quyên góp</strong> của bạn, xin vui lòng <a href="<c:url value="/register" context="${pageContext.request.contextPath}"></c:url>">đăng ký tài khoản</a>
                  hoặc <a href="<c:url value="/login" context="${pageContext.request.contextPath}"></c:url>">đăng nhập</a> trước khi thực hiện các bước liệt kê dưới đây: </em></p>
            </sec:authorize>
            <p>Các bước để quyên góp cho dự án <em id="spanProjectName"></em></p>
            <ol>
              <li>Chuyển khoản đến số tài khoản
                <em id="spanBeneficiaryAccountNumber"></em> (đơn vị <em id="spanCharityName"></em>)
              </li>
              <li>
                Điền đầy đủ các thông tin vào form bên dưới
              </li>
              <li>
                Dựa vào thông tin mà bạn cung cấp, chúng tôi sẽ xác thực lại với ngân hàng và đơn vị quyên góp.
              </li>
            </ol>
          </div>
          <form action="<c:url value="/donate" context="${pageContext.request.contextPath}"></c:url>"
                method="post"
                style="margin: 0 auto"
                id="donateForm"
          >
            <div class="m-3">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <input type="hidden" id="modalProjectId" name="projectId" value="" />

              <div class="form-group row mb-3 align-items-center">
                <label class="col-sm-4 col-form-label">Họ và tên:</label>
                <div class="col-sm-8">
                  <sec:authorize access="!isAuthenticated()">
                    <input type="text" class="form-control" id="fullName" name="fullName" value="" />
                  </sec:authorize>
                  <sec:authorize access="isAuthenticated()">
                  <input type="text" class="form-control" id="fullName" name="fullName" value="<sec:authentication property="principal.fullName"/>" />
                  </sec:authorize>
                </div>
              </div>

              <div class="form-group row mb-3 align-items-center">
                <label class="col-sm-4 col-form-label">Số tiền:</label>
                <div class="col-sm-8">
                  <input type="number" class="form-control" id="amount" name="amount" value="" />
                </div>
              </div>

              <div class="form-group row mb-3 align-items-center">
                <label class="col-sm-4 col-form-label">Mã số giao dịch:</label>
                <div class="col-sm-8">
                  <input type="text" class="form-control" id="trans_ref_no" name="trans_ref_no" value="" />
                </div>
              </div>

              <div class="form-group row mb-3 align-items-center">
                <label class="col-sm-4 col-form-label">Tin nhắn:</label>
                <div class="col-sm-8">
                  <textarea type="text" class="form-control" id="message" name="message" row="5" value=""></textarea>
                </div>


              </div>

            </div>
          </form>
            <p>Xin trân trọng cảm ơn.</p>
          <p>Ban quản trị hệ thống quyên góp từ thiện ilovevn.</p>
        </div>
        <div class="modal-footer">
          <button type="submit" form="donateForm" class="btn btn-primary">Gửi thông tin</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
        </div>
      </div>
    </div>
  </div>

  <jsp:include page="footer.jsp"/>

  <jsp:include page="common/jsFramework.jsp"/>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

  <script src="js/homepage_render.js">
  </script>



  </body>

  </html>