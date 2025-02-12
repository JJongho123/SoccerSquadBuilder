$(document).ready(function() {
    // 팀 생성 버튼 클릭 시 팝업 열기
    $('.create-team-btn').on('click', function() {
        $('#teamPopup').fadeIn(300);
    });

    // 취소 버튼 클릭 시 팝업 닫기
    $('.popup-content button[type="button"]').on('click', function() {
        $('#teamPopup').fadeOut(300);
    });

    // 팝업 외부 클릭 시 닫기
    $(document).on('click', function(e) {
        if ($(e.target).is('#teamPopup')) {
            $('#teamPopup').fadeOut(300);
        }
    });

    // 폼 제출 처리
    $('#teamCreateForm').on('submit', function(e) {
        e.preventDefault();

        // 폼 데이터 수집
        const formData = {
            teamName: $('#teamName').val(),
            teamMemberMaxCount: $('#teamMemberMaxCount').val(),
            teamLevel: $('#teamLevel').val(),
            teamActivityArea: $('#teamActivityArea').val()
        };

        console.log('팀 생성 데이터:', formData);

        // API 호출 예시
        /*
        $.ajax({
            url: '/api/teams',
            type: 'POST',
            data: formData,
            success: function(response) {
                alert('팀이 성공적으로 생성되었습니다.');
                $('#teamPopup').fadeOut(300);
            },
            error: function(xhr, status, error) {
                alert('팀 생성 중 오류가 발생했습니다.');
            }
        });
        */

        // 임시로 팝업만 닫기
        $('#teamPopup').fadeOut(300);
    });
});