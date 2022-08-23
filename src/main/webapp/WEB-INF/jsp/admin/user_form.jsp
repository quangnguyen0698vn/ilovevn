<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:out value="${title}"></c:out>
    </title>
    <jsp:include page='../common/cssFramework.jsp'/>
</head>
<body>
<span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
<h3 class="text-center">
    <c:out value="${heading}"></c:out>
</h3>
<form action="<c:url value="/admin/users/update" context="${pageContext.request.contextPath}"></c:url>"
      method="post"
      style="max-width: 600px; margin: 0 auto"
      id="userForm"
>
    <div class="border border-secondary rounded p-3">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <c:if test="${user != null}">
            <input hidden type="number" name="id" value="${user.id}"/>
        </c:if>


        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Họ và tên:</label>
            <div class="col-sm-8">
                <c:if test="${user != null}">
                    <input disabled type="text" class="form-control" id="fullName" name="fullName" value="<c:out value="${user.fullName}"></c:out>" />
                    <input hidden type="text" class="form-control" id="fullName" name="fullName" value="<c:out value="${user.fullName}"></c:out>" />
                </c:if>
                <c:if test="${user == null}">
                    <input type="text" class="form-control" id="fullName" name="fullName" value="" />
                </c:if>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Địa chỉ Email:</label>
            <div class="col-sm-8">
                <c:if test="${user != null}">
                    <input disabled type="email" class="form-control" id="email" name="email" value="<c:out value="${user.email}"></c:out>" />
                    <input hidden type="email" class="form-control" id="email" name="email" value="<c:out value="${user.email}"></c:out>" />

                </c:if>

                <c:if test="${user == null}">
                    <input type="email" class="form-control" id="email" name="email" value="" />
                </c:if>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Địa chỉ:</label>
            <div class="col-sm-8">
                <c:if test="${user != null}">
                    <input disabled type="text" class="form-control" id="address" name="address" value="<c:out value="${user.address}"></c:out>" />
                    <input hidden type="text" class="form-control" id="address" name="address" value="<c:out value="${user.address}"></c:out>" />
                </c:if>

                <c:if test="${user == null}">
                    <input type="text" class="form-control" id="address" name="address" value="" />
                </c:if>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Số điện thoại:</label>
            <div class="col-sm-8">
                <c:if test="${user != null}">
                    <input disabled type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<c:out value="${user.phoneNumber}"></c:out>" />
                    <input hidden type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<c:out value="${user.phoneNumber}"></c:out>" />
                </c:if>

                <c:if test="${user == null}">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="" />
                </c:if>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Vai trò:</label>
            <div class="col-sm-8">
                <select name="roleId" id="roleId" class="form-select"
                        <c:if test="${user != null && user.role.id == 1}">disabled</c:if>
                >
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.id}"
                                <c:if test="${user != null && user.role.id == role.id}">selected</c:if>
                        >
                            <c:out value="${role.getRoleName()}"></c:out>
                        </option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label class="col-sm-4 col-form-label">Trạng Thái:</label>
            <div class="col-sm-8">
                <select name="enabled" id="enabled" class="form-select"
                        <c:if test="${user != null && user.role.id == 1}">disabled</c:if>
                >
                    <option value="1" class="enabled-status"
                            <c:if test="${user != null && user.enabled}">selected</c:if>
                    >Đang hoạt động</option>
                    <option value="0" class="disabled-status"
                            <c:if test="${user != null && !user.enabled}">selected</c:if>
                    >Vô hiệu hóa</option>
                </select>
            </div>
        </div>

        <div class="text-center">
            <c:if test="${user != null}">
                <input type="submit" value="Cập nhập" class="btn btn-primary" />

            </c:if>

            <c:if test="${user == null}">
                <input type="submit" value="Tạo tài khoản" class="btn btn-primary" />
            </c:if>
        </div>
    </div>
</form>


<jsp:include page="../common/jsFramework.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script>
    $("#roleId").change(function () {
       let newRole = $(this).val();
       // console.dir(newRole);
       if (newRole == 1) {
           // console.log('Line 140');
           $(".enabled-status").attr('selected', 'selected');
           $(".disabled-status").hide();
       } else {
           $(".disabled-status").show();
       }
    });
</script>

<script src="<c:url value="/js/user_validate_form.js" context="${pageContext.request.contextPath}"></c:url>">
</script>

</body>
</html>
