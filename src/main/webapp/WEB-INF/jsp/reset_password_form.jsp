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
    <c:set var="postUri" value="/resetPassword"></c:set>
    <div class="container mt-4">
        <form action="<c:url value="${postUri}" context="${pageContext.request.contextPath}"></c:url>"
              method="post"
              style="max-width: 600px; margin: 0 auto"
              id="resetPasswordForm"
              class="mb-5"
        >
            <div class="border border-secondary rounded p-3">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="token" value="${token}"/>

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

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Mật khẩu mới:</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="password" name="password" value="" />
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label class="col-sm-4 col-form-label">Nhập lại mật khẩu:</label>
                    <div class="col-sm-8">
                        <input type="password" class="form-control" id="repeatedPassword" name="repeatedPassword" value="" />
                    </div>
                    <c:if test="${checkRepeatedPassword != null}">
                        <p class="text-danger"><c:out value="${checkRepeatedPasswordMessage}"></c:out></p>
                    </c:if>
                </div>

                <div class="text-center">
                    <input type="submit" value="Đổi mật khẩu" class="btn btn-primary" />
                </div>
            </div>
        </form>
    </div>


    <jsp:include page="footer.jsp"/>


    <jsp:include page="common/jsFramework.jsp"></jsp:include>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script>
        $.validator.addMethod("strong_password", function (value, element) {
            let password = value;
            // if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&])(.{8,20}$)/.test(password))) {
            if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(.{8,20}$)/.test(password))) {
                return false;
            }
            return true;
        }, function (value, element) {
            let password = $(element).val();
            if (!(/^(.{8,20}$)/.test(password))) {
                return 'Độ dài mật khẩu phải từ 8-20 kí tự.';
            }
            else if (!(/^(?=.*[A-Z])/.test(password))) {
                return 'Mật khẩu cần có ít nhất một kí tự in HOA.';
            }
            else if (!(/^(?=.*[a-z])/.test(password))) {
                return 'Mật khẩu cần có ít nhất một kí tự in thường.';
            }
            else if (!(/^(?=.*[0-9])/.test(password))) {
                return 'Mật khẩu cần có ít nhất một chữ số.';
            }
            // else if (!(/^(?=.*[@#$%&])/.test(password))) {
            //     return "Mật khẩu cần có ít nhất 1 trong các ký tự đặc biệt sau @#$%&.";
            // }
            return false;
        });

        $("#resetPasswordForm").validate({
            rules: {
                password: {
                    strong_password: true
                },
                repeatedPassword: {
                    equalTo: "#password"
                },
            },

            messages: {
                password: {
                },
                repeatedPassword: {
                    equalTo: "Password NOT match"
                },
            }
        });
    </script>

</body>
</html>
