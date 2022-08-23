const contextPath = $("#contextPath").text();

$.flag = false;
$.message = '';

$(document).ready(function() {
    $.validator.addMethod("check_email_unique", function(value, element) {
        let userEmail = value;
        console.log('userEmail: ' + userEmail);
        const url = contextPath + "/api/users/check_unique_email";

        csrfValue = $("input[name='_csrf']").val();
        params = {email: userEmail, _csrf: csrfValue};

        console.log('csrfValue = ' + csrfValue);

        // const params = {email: userEmail}

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
        csrfValue = $("input[name='_csrf']").val();
        params = {email: userEmail, _csrf: csrfValue};
        // const params = {email: userEmail};
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

    $("#userForm").validate({
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