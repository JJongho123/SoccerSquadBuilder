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

    if ($("#userIdError").hasClass("error-message")) {
        alert("ID 중복확인!!!")
        return;
    }

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
        success: function () {
            alert("회원가입 성공");
            window.location.href = '/login';
        },
        error: function (error) {
            console.error(error.responseJSON);
            alert(error.responseJSON.data.errorMsg);
        }
    });
});

let typingTimer;
const doneTypingInterval = 500; // 0.5초

// user_id 입력 필드에 이벤트 리스너 추가
$('#user_id').on('input', function () {
    clearTimeout(typingTimer);
    const userId = $(this).val();

    // 입력값이 비어있으면 메시지 초기화
    if (userId.length === 0) {
        $('#userIdError').text('').removeClass('valid-message error-message');
        return;
    }

    // 사용자가 타이핑을 멈추면 중복 체크 실행
    typingTimer = setTimeout(function () {
        checkDuplicateId(userId);
    }, doneTypingInterval);
});

function checkDuplicateId(userId) {
    const params = {
        userId: userId
    }
    $.ajax({
        url: '/api/user/check-duplicate-id',
        method: 'POST',
        data: params,
        success: function (ajaxData) {
            const errorDiv = $('#userIdError');

            if (ajaxData.data) {
                // 중복된 아이디인 경우
                errorDiv.text('이미 사용중인 아이디입니다.')
                    .removeClass('valid-message')
                    .addClass('error-message');
            } else {
                // 사용 가능한 아이디인 경우
                errorDiv.text('사용 가능한 아이디입니다.')
                    .removeClass('error-message')
                    .addClass('valid-message');
            }
        },
        error: function () {
            $('#userIdError').text('서버 오류가 발생했습니다.')
                .removeClass('valid-message')
                .addClass('error-message');
        }
    });
}