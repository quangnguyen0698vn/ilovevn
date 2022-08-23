<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><c:out value="${title}"></c:out></title>
    <jsp:include page="common/cssFramework.jsp"></jsp:include>
</head>
<body class="d-flex flex-column min-vh-100">
    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

    <jsp:include page="header.jsp"/>

    <h3 class="text-center"><c:out value="${title}"></c:out></h3>
    <c:set var="postUri" value="${user == null ? \"/users/create_new_user\" : \"/users/saveInfo\"}"></c:set>
    <div class="container mt-4">
        <form action="<c:url value="${postUri}" context="${pageContext.request.contextPath}"></c:url>"
              method="post"
              style="max-width: 600px; margin: 0 auto"
              id="registrationForm" enctype="multipart/form-data"
              class="mb-5"
        >
            <div class="border border-secondary rounded p-3">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Họ và tên:</label>
                    <div class="col-sm-8">
                        <input <c:if test="${user != null}">disabled</c:if> type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" />
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Địa chỉ Email:</label>
                    <div class="col-sm-8">
                        <input <c:if test="${user != null}">disabled</c:if> type="email" class="form-control" id="email" name="email" value="${user.email}" />
                    </div>
                </div>

                <%--            <div class="form-group row mb-3">--%>
                <%--                <label class="col-sm-4 col-form-label">Mật khẩu:</label>--%>
                <%--                <div class="col-sm-8">--%>
                <%--                    <input type="password" class="form-control" id="password" name="password" value="" />--%>
                <%--                </div>--%>
                <%--            </div>--%>

                <%--            <div class="form-group row mb-3">--%>
                <%--                <label class="col-sm-4 col-form-label">Nhập lại mật khẩu:</label>--%>
                <%--                <div class="col-sm-8">--%>
                <%--                    <input type="password" class="form-control" id="repeatedPassword" name="repeatedPassword" value="" />--%>
                <%--                </div>--%>
                <%--            </div>--%>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Địa chỉ:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="address" name="address" value="<c:out value="${user.address}"></c:out>" />
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Số điện thoại:</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<c:out value="${user.phoneNumber}"></c:out>" />
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Avatar</label>
                    <div class="col-sm-8">
                        <input type="file" id="fileImage" name="fileImage" accept="image/png, image/jpeg">
                    </div>
                </div>



                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label"></label>
                    <div class="col-sm-8">
                        <c:if test="${user == null}">
                            <img id="thumbnail" alt="Hình ảnh" class="rounded-circle" width="150" src="${pageContext.request.contextPath}/images/image-thumbnail.png"/>
                        </c:if>
                        <c:if test="${user != null}">
                            <img id="thumbnail" alt="Hình ảnh" class="rounded-circle" width="150" src="${pageContext.request.contextPath}${user.profilePhotoPath}"/>
                        </c:if>
                    </div>
                </div>

                <div class="text-center">
                    <c:if test="${user == null}">
                        <input type="submit" value="Tạo tài khoản" class="btn btn-primary" />
                    </c:if>
                    <c:if test="${user != null}">
                        <input type="submit" value="Cập nhật thông tin" class="btn btn-primary" />
                    </c:if>
                </div>
            </div>
        </form>
    </div>


    <jsp:include page="footer.jsp"/>


    <jsp:include page="common/jsFramework.jsp"></jsp:include>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script src="<c:url value="/js/user_validate_form.js" context="${pageContext.request.contextPath}"></c:url>">
    </script>

    <script>
        $("#fileImage").change(function() {
            console.dir(this);
            var file = this.files[0];
            var fileName = file.name;
            console.log(fileName);

            var reader = new FileReader();
            reader.onload = function(e) {
                $("#thumbnail").attr("src", e.target.result);
            };

            reader.readAsDataURL(file);
        })
    </script>

</body>
</html>
