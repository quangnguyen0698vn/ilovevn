<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="mx-auto" style="width: 85%;">
  <h2>Quản lý các dự án quyên góp từ thiện</h2>

  <c:if test="${message!=null}">
    <c:if test="${success == false}"><h4 class="text-danger my-3"><c:out value="${message}"></c:out></h4></c:if>
    <c:if test="${success == true}"><h4 class="text-success my-3"><c:out value="${message}"></c:out></h4></c:if>
  </c:if>

  <a href="
       ${pageContext.request.contextPath}/admin/projects/createForm
    " class="btn btn-primary m-3">Thêm Dự Án</a>

  <a href="#" id="bulk-selector-enable" class="btn btn-warning m-3">Chọn nhiều dự án</a>
  <a href="#" id="bulk-selector-disable" class="btn btn-warning m-3" hidden>Tắt chọn nhiều dự án</a>
  <button type="submit" id="bulk-delete-button" form="hidden-form" class="btn btn-danger" hidden>Xóa các projects đã chọn</button>

  <!-- Filter and Search -->
  <h4 class="text-muted mt-5">Lọc và tìm kiếm dự án</h4>
  <form action="" id="searchForm" class="row mt-2">
    <div class="col-md-5 col-12">
      <label for="dropdownCharity">Tổ chức:</label>
      <select class="form-control" name="charityId" id="dropdownCharity">
        <option value="0">Tất cả tổ chức từ thiện</option>
        <c:forEach items="${charities}" var="charity">
          <c:if test="${charity.id == selectedCharityId}">
            <option value="<c:out value="${charity.id}"></c:out>" selected>
              <c:out value="${charity.name}"></c:out>
            </option>
          </c:if>
          <c:if test="${charity.id != selectedCharityId}">
            <option value="<c:out value="${charity.id}"></c:out>">
              <c:out value="${charity.name}"></c:out>
            </option>
          </c:if>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-5 col-12">
      <label for="keyword">Từ khóa:</label>
      <input
              type="search"
              name="keyword"
              class="form-control"
              id="keyword"
              placeholder="Nhập từ khóa bạn muốn tìm kiếm"
              value="<c:out value="${currentKeyword}" default=""></c:out>"
      />
    </div>


    <div class="col-md-5 col-12">
      <label for="sortField">Sắp xếp theo</label>
      <select class="form-control" name="sortField" id="sortField">
        <c:forEach items="${sortFieldValues}" varStatus="status">
          <c:if test="${sortFieldValues[status.index] == sortField}">
            <option selected value="${sortFieldValues[status.index]}">
              <c:out value="${sortFieldNames[status.index]}"></c:out>
            </option>
          </c:if>
          <c:if test="${sortFieldValues[status.index] != sortField}">
            <option value="${sortFieldValues[status.index]}">
              <c:out value="${sortFieldNames[status.index]}"></c:out>
            </option>
          </c:if>
        </c:forEach>
      </select>
    </div>

    <div class="col-md-5 col-12">
      <label for="sortDir">Thứ tự</label>
      <select class="form-control" name="sortDir" id="sortDir">
        <c:forEach items="${sortDirValues}" varStatus="status">
          <c:if test="${sortDirValues[status.index] == sortDir}">
            <option selected value="${sortDirValues[status.index]}">
              <c:out value="${sortDirNames[status.index]}"></c:out>
            </option>
          </c:if>
          <c:if test="${sortDirValues[status.index] != sortDir}">
            <option value="${sortDirValues[status.index]}">
              <c:out value="${sortDirNames[status.index]}"></c:out>
            </option>
          </c:if>
        </c:forEach>
      </select>
    </div>


    <div class="col-md-2 col-12">
      <button type="submit" class="btn btn-primary px-1 mx-2 mt-2">
        <i class="fas fa-search fa-2x"></i>
      </button>

      <button
              type="button"
              class="btn btn-secondary px-1 mx-2 mt-2"
              onclick="window.location.href=window.location.origin+'/ilovevn/admin/projects/'"
      >
        <i class="fas fa-eraser fa-2x"></i>
      </button>
    </div>
  </form>
</section>