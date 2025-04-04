document.querySelector(".login-btn").addEventListener("click", function (event) {
    event.preventDefault(); // 기본 제출 방지

    const params = {
        userId: $('#user_id').val(),
        passwd: $('#passwd').val()
    };

    $.ajax({
        url: '/api/auth/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(params),
        xhrFields: {
            withCredentials: true // 쿠키를 주고받기 위해 필요
        },
        success: function (ajaxData) {
            if (ajaxData.data === true) {
                window.location.href = '/squadBuilder';
            } else {
                alert('로그인에 실패했습니다.');
            }
        },
        error: function (xhr, status, error) {
            alert('로그인에 실패했습니다.');
            console.error('Error:', error);
        }
    });
});

// 회원가입 버튼 클릭 시 /api/register 페이지로 이동
document.querySelector(".register-btn").addEventListener("click", function (event) {
    event.preventDefault();
    window.location.href = '/register';
});
