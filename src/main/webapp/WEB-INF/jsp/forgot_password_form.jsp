<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><c:out value="${title}"></c:out></title>
    <jsp:include page="common/cssFramework.jsp"></jsp:include>
</head>
<body class="d-flex flex-column min-vh-100">
<span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>

<jsp:include page="header.jsp"/>

<h3 class="text-center"><c:out value="${title}"></c:out></h3>
<div class="container mt-4">

    <c:if test="${sendEmail == false}">
        <form action="<c:url value="/forgotPassword" context="${pageContext.request.contextPath}"></c:url>"
    </c:if>
    <c:if test="${sendEmail == true}">
    <form id="forgetPasswordForm" action="<c:url value="/resetPasswordForm" context="${pageContext.request.contextPath}"></c:url>"
    </c:if>
          method="post"
          style="max-width: 600px; margin: 0 auto"
          id="loginForm"
    >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="border border-secondary rounded p-3">

        <c:if test="${error}">
            <p class="text-danger"><c:out value="${errorMessage}"></c:out></p>
        </c:if>

        <c:if test="${sendEmail == false}">
            <p>Chúng tôi sẽ gửi mã token thay đổi mật khẩu đến email của bạn</p>
        </c:if>

        <c:if test="${sendEmail == true}">
            <p class="text-success"><c:out value="${sendEmailMessage}"></c:out></p>
        </c:if>

            <div>
                <c:if test="${sendEmail == false}">
                    <p>
                        <c:if test="${email != null && emailNotFound}"><p class="text-danger"><c:out value="${emailNotFoundMessage}"></c:out></p></c:if>
                        <input type="email" name="email" class="form-control" placeholder="Nhập email của bạn" required autofocus/>
                    </p>
                </c:if>

                <c:if test="${sendEmail == true}">
<%--                    <p class="text-success">Bạn đang thực hiện thay đổi mật khẩu cho tài khoản <c:out value="${email}"></c:out></p>--%>
                    <p>
                        <input type="text" id="resetToken" name="resetToken" class="form-control" placeholder="Nhập mã token" required autofocus/>
                        <input type="hidden" name="email" value="<c:out value="${email}"></c:out>"/>
                    </p>
                </c:if>
                <p class="text-center">
                    <c:if test="${sendEmail == false}">
                        <input type="submit" value="Gửi token đến email của tôi" class="btn btn-primary" />
                    </c:if>

                    <c:if test="${sendEmail == true}">
                        <input type="submit" value="Đổi mật khẩu" class="btn btn-primary" />
                    </c:if>
                </p>
            </div>

        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>


<jsp:include page="common/jsFramework.jsp"></jsp:include>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
    jQuery.validator.addMethod("exactlength", function(value, element, param) {
        return this.optional(element) || value.length == param;
    }, $.validator.format("Please enter exactly {0} characters."))

    $("#forgetPasswordForm").validate({
        rules: {
            resetToken: {
                exactlength: 30,
            }
        },
        messages: {
            resetToken: {
                exactlength: 'Token có độ dài đúng 30 ký tự'
            }
        }
    });
</script>
</body>
</html>
