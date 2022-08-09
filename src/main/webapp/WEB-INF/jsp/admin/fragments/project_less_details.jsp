<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="less-details">
  <div class="row m-1">
    <c:forEach items="${projects}" var="project" varStatus="status">

      <div class="card mb-3">

        <img src="<c:url value="${project.getImagePath()}"></c:url>" class="card-img-top img-fluid" alt="Hình minh họa" />
        <div class="card-body">
          <h5 class="card-title"><c:out value="${project.name}"></c:out></h5>
          <p class="card-text">Số tiền quyên góp được <span><fmt:formatNumber value="${project.raisedAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>
          <p class="card-text">Số tiền dự kiến quyên góp <span><fmt:formatNumber value="${project.targetAmount}" type="currency" maxFractionDigits="0" currencyCode="VND" /></span></p>
          <p class="card-text">Ngày hết hạn <span><fmt:formatDate value="${project.expiredDate}" pattern="dd-MM-yyyy" /></span></p>
        </div>
        <div class="text-center">
          <a
                  class="link-edit"
                  href="<c:url value="/admin/projects/edit/${project.id}" context="${pageContext.request.contextPath}"></c:url>"
                  title="Sửa thông tin dự án"
          >
            <i class="fas fa-edit fa-2x icon-green"></i>
          </a>
          &nbsp;
          <c:if test="${!project.isAlreadyStarted()}">
            <%--                                    <c:out value="${project.isAlreadyStarted()}"></c:out>--%>
            <a class="link-delete"
               href="javascript:deleteProject(${project.id}, '${project.name}')"
               data-project-id = "<c:out value="${project.id}"></c:out>"
               title="Xóa dự án này">
              <i class="fas fa-trash fa-2x icon-dark"></i>
            </a>
          </c:if>
        </div>
      </div>
    </c:forEach>
  </div>
</div>