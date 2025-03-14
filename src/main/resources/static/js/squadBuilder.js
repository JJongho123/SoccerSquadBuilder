import {getUserData} from "./util/userUtil.js";

let userInfo;

$(document).on('click', '.join-team-btn', function () {
    const teamId = $(this).data('team-id');
    if (confirm("가입하시겠습니까?")) {
        requestJoinTeam(teamId);
    }
});

$(document).ready(async function () {

    userInfo = await getUserData();
    if (userInfo.role) {
        $(".create-team-btn").remove();
    }

    // 로그아웃
    $('#logout').on('click', function () {
        $.ajax({
            url: '/api/auth/logout',
            type: 'PUT',
            contentType: 'application/json',
            success: function () {
                // 로그아웃 성공 시 로그인 페이지로 이동
                window.location.href = "/login";
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });

    // 팀 생성 버튼 클릭 시 팝업 열기
    $('.create-team-btn').on('click', function () {
        $('#teamPopup').fadeIn(300);
    });

    $('#createTeamBtn').on('click', function () {
        const params = {
            teamActivityArea: $('#teamActivityArea').val(),
            teamLevel: $('#teamLevel').val(),
            teamMemberMaxCount: $('#teamMemberMaxCount').val(),
            teamName: $('#teamName').val()
        }


        $.ajax({
            url: '/api/team',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(params),
            success: function () {
                alert("팀 생성 성공")
                window.location.reload();
            },
            error: function (xhr, status, error) {
                alert('팀 생성 실패');
                console.error('Error:', error);
            }
        });
    });

    // 취소 버튼 클릭 시 팝업 닫기
    $('.popup-content button[type="button"]').on('click', function () {
        $('#teamPopup').fadeOut(300);
    });

    // 팝업 외부 클릭 시 닫기
    $(document).on('click', function (e) {
        if ($(e.target).is('#teamPopup')) {
            $('#teamPopup').fadeOut(300);
        }
    });

    // 폼 제출 처리
    $('#teamCreateForm').on('submit', function (e) {
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


$(document).ready(function () {
    // 팀 목록 로드
    loadTeams();

    // 팀 카드 클릭 이벤트 (동적 생성된 요소에 대한 이벤트 바인딩)
    // $(document).on('click', '.team-card', function() {
    //     const teamId = $(this).data('team-id');
    //     loadTeamDetail(teamId);
    // });

    // 상세 팝업 닫기
    $('.close-btn').on('click', function () {
        $('#teamDetailPopup').fadeOut(300);
    });

});

// 팀 목록 로드 함수
function loadTeams() {
    $.ajax({
        url: '/api/team/list',
        type: 'GET',
        success: function (ajaxData) {
            const teamList = $('.team-list');
            teamList.empty();
            const teams = ajaxData.data

            teams.forEach(team => {
                teamList.append(`
                    <div class="team-card" data-team-id="${team.id}">
                        <h3>${team.teamName}</h3>
                        <p>활동지역: ${team.teamActivityArea}</p>
                        <p>팀 실력: ${team.teamLevel}</p>
                        <p>인원: ${team.teamMemberCount}/${team.teamMemberMaxCount}</p>
                        <p>팀장: ${team.userId}</p>
                        <button data-team-id="${team.id}" class="join-team-btn">가입</button>
                    </div>
                `);
            });
        },
        error: function (xhr, status, error) {
            alert('팀 목록을 불러오는데 실패했습니다.');
        }
    });
}

// 팀 상세 정보 로드 함수
function loadTeamDetail(teamId) {
    $.ajax({
        url: `/api/teams/${teamId}`,  // 실제 API 엔드포인트로 수정 필요
        type: 'GET',
        success: function (team) {
            // 상세 정보 팝업 데이터 설정
            $('#detailTeamName').text(team.teamName);
            $('#detailLeader').text(team.leaderName);
            $('#detailMaxMembers').text(team.maxMembers);
            $('#detailCurrentMembers').text(team.currentMembers);
            $('#detailTeamLevel').text(team.teamLevel);
            $('#detailLocation').text(team.location);

            // 가입 신청 버튼에 팀 ID 설정
            $('#joinTeam').data('team-id', teamId);

            // 팝업 표시
            $('#teamDetailPopup').fadeIn(300);
        },
        error: function (xhr, status, error) {
            alert('팀 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 가입 신청 함수
function requestJoinTeam(teamId) {

    const params = {
        teamId: teamId,
        userFk: userInfo.id
    }

    $.ajax({
        url: `/api/team/join`,  // 실제 API 엔드포인트로 수정 필요
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(params),
        success: function () {
            alert('가입 신청이 완료되었습니다.');
            $('#teamDetailPopup').fadeOut(300);
        },
        error: function (xhr, status, error) {
            const errorMessage = xhr.responseJSON?.message || '가입 신청 중 오류가 발생했습니다.';
            alert(errorMessage);
        }
    });
}


