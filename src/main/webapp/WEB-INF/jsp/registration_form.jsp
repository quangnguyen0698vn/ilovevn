<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="common/cssFramework.jsp"></jsp:include>
</head>
<body>
    <span id="contextPath" style="display:none;"><c:out value="${pageContext.request.contextPath}"></c:out></span>
    <h3 class="text-center">Đăng ký tài khoản</h3>
    <form action="
            <c:url value="/users/create_new_user" context="${pageContext.request.contextPath}"></c:url>"
          method="post"
          style="max-width: 600px; margin: 0 auto"
          id="registrationForm"
    >
        <div class="border border-secondary rounded p-3">
            <div class="form-group row mb-3">
                <label class="col-sm-4 col-form-label">Họ và tên:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="fullName" name="fullName" value="" />
                </div>
            </div>

            <div class="form-group row mb-3">
                <label class="col-sm-4 col-form-label">Địa chỉ Email:</label>
                <div class="col-sm-8">
                    <input type="email" class="form-control" id="email" name="email" value="" />
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
                    <input type="text" class="form-control" id="address" name="address" value="" />
                </div>
            </div>

            <div class="form-group row mb-3">
                <label class="col-sm-4 col-form-label">Số điện thoại:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="" />
                </div>
            </div>

            <div class="text-center">
                <input type="submit" value="Tạo tài khoản" class="btn btn-primary" />
            </div>
        </div>
    </form>


    <jsp:include page="common/jsFramework.jsp"></jsp:include>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.5/jquery.validate.min.js" integrity="sha512-rstIgDs0xPgmG6RX1Aba4KV5cWJbAMcvRCVmglpam9SoHZiUCyQVDdH2LPlxoHtrv17XWblE/V/PP+Tr04hbtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <script>




        const contextPath = $("#contextPath").text();

        $.flag = false;
        $.message = '';

        $(document).ready(function() {
            $.validator.addMethod("check_email_unique", function(value, element) {
               let userEmail = value;
               console.log('userEmail: ' + userEmail);
               const url = contextPath + "/api/users/check_unique_email";

                // csrfValue = $("input[name='_csrf']").val();
                // params = {email: userEmail, _csrf: csrfValue};

                const params = {email: userEmail}

                $.post(url, params, function (response) {
                    console.log('response 1: ' + response);
                    if (response == "OK") {
                        $.flag = false;
                        $.message = '';
                        return true;
                    } else if (response === "Duplicated") {
                        $.flag = true;
                        $.message = userEmail + " đã tồn tại trong hệ thống";
                        return false;
                    } else {
                        $.flag = true;
                        $.message = "Unknown response from server";
                        return false;
                    }

                }).fail(function () {
                    $.flag = true;
                    $.message = "Could not connect to the server";
                    return false;
                });

                return !$.flag;
            }, function(value, element) {
                    let userEmail = $(element).val();
                    const url = contextPath + "/api/users/check_unique_email";
                    // csrfValue = $("input[name='_csrf']").val();
                    // params = {email: userEmail, _csrf: csrfValue};
                    const params = {email: userEmail};
                    $.post(url, params, function (response) {
                        console.log('response 2: ' + response);
                        if (response == "OK") {
                            $.flag = false;
                            $.message = '';
                            return true;
                        } else if (response === "Duplicated") {
                            $.flag = true;
                            $.message = userEmail + " đã tồn tại trong hệ thống";
                            return false;
                        } else {
                            $.flag = true;
                            $.message = "Unknown response from server";
                            return false;
                        }

                    }).fail(function () {
                        $.flag = true;
                        $.message = "Could not connect to the server";
                        return false;
                    });

                    console.log('the flag: ' + $.flag);
                    console.log('the message: ' + $.message);
                    if ($.flag === true) {
                        return $.message;
                    }

                    if ($.flag) return  $.message;
                    return false;
            });

            // $.validator.addMethod("strong_password", function (value, element) {
            //     let password = value;
            //     if (!(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%&])(.{8,20}$)/.test(password))) {
            //         return false;
            //     }
            //     return true;
            // }, function (value, element) {
            //     let password = $(element).val();
            //     if (!(/^(.{8,20}$)/.test(password))) {
            //         return 'Độ dài mật khẩu phải từ 8-20 kí tự.';
            //     }
            //     else if (!(/^(?=.*[A-Z])/.test(password))) {
            //         return 'Mật khẩu cần có ít nhất một kí tự in HOA.';
            //     }
            //     else if (!(/^(?=.*[a-z])/.test(password))) {
            //         return 'Mật khẩu cần có ít nhất một kí tự in thường.';
            //     }
            //     else if (!(/^(?=.*[0-9])/.test(password))) {
            //         return 'Mật khẩu cần có ít nhất một chữ số.';
            //     }
            //     else if (!(/^(?=.*[@#$%&])/.test(password))) {
            //         return "Mật khẩu cần có ít nhất 1 trong các ký tự đặc biệt sau @#$%&.";
            //     }
            //     return false;
            // });

            $("#registrationForm").validate({
                rules: {
                    fullName: {
                        required: true
                    },
                    email: {
                        check_email_unique: true,
                        required: true,
                    },
                    // password: {
                    //     strong_password: true
                    // },
                    // repeatedPassword: {
                    //     equalTo: "#password"
                    // },
                    phoneNumber: {
                        digits: true,
                    }
                },

                messages: {
                    fullName: {
                      required: 'Bạn phải nhập họ và tên'
                    },
                    email: {
                        required: 'Trường này không được để trống',
                    },
                    // password: {
                    // },
                    // repeatedPassword: {
                    //     equalTo: "Password NOT match"
                    // },
                    phoneNumber: {
                        digits: 'Chỉ nhập số 0-9 cho trường số điện thoại',
                    }
                }
            });
        });
    </script>

</body>
</html>
