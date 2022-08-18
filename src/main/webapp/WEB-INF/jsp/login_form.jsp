<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="common/cssFramework.jsp"></jsp:include>
</head>
<body>
    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
    <h3 class="text-center">Đăng nhập vào hệ thống</h3>
    <form action="<c:url value="/login" context="${pageContext.request.contextPath}"></c:url>"
          method="post"
          style="max-width: 600px; margin: 0 auto"
          id="loginForm"
    >
        <div class="border border-secondary rounded p-3">
            <p>Mời bạn nhập thông tin đăng nhập</p>
            <p>
                <input type="email" name="email" class="form-control" placeholder="E-mail">
            </p>
            <p>
                <input type="password" name="password" class="form-control" placeholder="Password">
            </p>
            <p>
                <input type="checkbox" name="remember-me">&nbsp;Remember Me
            </p>
            <p>
                <input type="submit" value="Login" class="btn btn-primary">
            </p>
        </div>
    </form>


    <jsp:include page="common/jsFramework.jsp"></jsp:include>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script>
        const contextPath = $("#contextPath").text();

            $("#loginForm").validate({
                rules: {
                    email: {
                        required: true
                    },

                    password: {
                        required: true
                    },
                },

                messages: {

                    email: {
                        required: 'Email không được để trống',
                    },
                    password: {
                        required: 'Mật khẩu không được để trống',
                    },
                }
            });
    </script>

</body>
</html>
