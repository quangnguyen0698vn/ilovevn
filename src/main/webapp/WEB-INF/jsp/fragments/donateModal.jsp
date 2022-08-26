<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
