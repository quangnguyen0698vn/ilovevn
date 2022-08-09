let validator;
let countErrors = 0;
$(document).ready(function() {
    $.validator.addMethod("dateNotLessThan",
        function(value, element, arg) {
            console.log("A: " + value);
            console.log("B: " + arg);
            return value >= arg;
        },
        "You cannot select a date smaller than today."
    );

    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = yyyy+"-"+mm+"-"+dd;

    let rules = {
        name: {
            required: true,
        },
        charityId: {
            min: 1
        },
        targetAmount: {
            required: true,
            min: 1000
        },
        startedDate: {
            date: true,
            required: true,
            dateNotLessThan: today
        },
        expiredDate: {
            date: true,
            required: true,
            dateNotLessThan: $("#startedDate").val()
        }
    }

    let messages = {
        name: {
            required: "Bạn phải nhập tên dự án",
        },
        charityId: {
            min: "Bạn hãy chọn một tổ chức đứng ra quyên góp"
        },
        targetAmount: {
            required: "Số tiền dự kiến quyên góp không được để trống",
            min: "Giá trị quyên góp ít nhất là 1000 đồng"
        },
        startedDate: {
            required: "Bạn phải chọn ngày bắt đầu quyên góp",
            dateNotLessThan: "Ngày bắt đầu quyên góp phải sau hoặc ít nhất bằng ngày hôm nay"
        },
        expiredDate: {
            required: "Bạn phải chọn ngày kết thúc quyên góp",
            dateNotLessThan: "Ngày kết thúc quyên góp phải lớn hơn hoặc bằng ngày bắt đầu"
        },
    }



    validator = $("#mainForm").validate({rules, messages,
        invalidHandler: function() {
            countErrors = validator.numberOfInvalids();
            alert( "Bạn cần kiểm tra lại " + countErrors + " trường thông tin chung của dự án!" );
        }
    });

    $("#startedDate").change(function() {
        $("#expiredDate").rules('remove', 'dateNotLessThan');
        const startedDate = $("#startedDate").val()
        console.log("New started date: " + startedDate);
        $("#expiredDate").rules('add', { dateNotLessThan: startedDate });
    });


});