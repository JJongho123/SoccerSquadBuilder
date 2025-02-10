

$.ajax({
    url: "/api/validation/password-policies",
    method: "GET",
    success: function (ajaxData) {
        const policies = ajaxData.data.policies;
        const policyList = $('#policyList');
        policies.forEach(policy => {
            policyList.append(`<li>${policy}</li>`);
        });
    },
    error: function (error) {
        console.error("데이터를 가져오는 데 실패했습니다:", error);
        alert("비밀번호 정책을 불러오는 데 실패했습니다. 다시 시도해주세요.");
    }
});


$('#registForm').on('submit', function (e) {

    const email = $("#email").val();
    const user_id = $("#user_id").val();
    const name = $("#username").val();
    const passwd = $("#passwd").val();

    const params = {
        email: email,
        userId: user_id,
        name: name,
        passwd: passwd
    }

    e.preventDefault();

    $.ajax({
        url: "/api/user",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(params),
        success: function (ajaxData) {
            if(ajaxData.data === true){
                alert("회원가입 성공");
                window.location.href = '/soccer';
            }
            else
                alert("회원가입 실패");
        },
        error: function (error) {
            console.error(error.responseJSON);
            alert(error.responseJSON.data.errorMsg);
        }
    });
});