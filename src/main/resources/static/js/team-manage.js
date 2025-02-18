import {getUserData} from "./util/userUtil.js";

let role;

$(document).ready(async function () {
    let page = 1;
    let loading = false;
    let hasMore = true;

    // 초기 데이터 로드
    const userData = await getUserData();
    console.log(userData)
    role = userData.role
    loadTeamInfo(parseInt(userData.teamId));
    loadMembers(page);

    // 스쿼드 유형 선택 시 버튼 활성화
    $('#squadType').on('change', function () {
        $('#createSquadBtn').prop('disabled', !$(this).val());
    });

    // 무한 스크롤
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            if (!loading && hasMore) {
                page++;
                loadMembers(page);
            }
        }
    });

    // 스쿼드 생성 버튼 클릭
    $('#createSquadBtn').on('click', function () {
        const squadType = $('#squadType').val();
        const selectedMembers = getSelectedMembers();

        if (validateSquadMembers(squadType, selectedMembers)) {
            createSquad(squadType, selectedMembers);
        }
    });
});

// 팀 정보 로드
function loadTeamInfo(teamId) {

    $.ajax({
        url: `/api/team/${teamId}`,  // 실제 API 엔드포인트로 수정 필요
        type: 'GET',
        success: function(team) {
            $('#totalMembers').text(team.totalMembers);
            $('#currentMembers').text(team.currentMembers);
            $('#maxMembers').text(team.maxMembers);
            $('#location').text(team.location);
            $('#teamLevel').text(team.teamLevel);
            $('#leaderName').text(team.leaderName);
        },
        error: function(xhr, status, error) {
            alert('팀 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 팀원 목록 로드 (무한 스크롤)
function loadMembers(page) {
    if (loading) return;

    loading = true;
    $('.loading-spinner').show();

    $.ajax({
        url: `/api/my-team/members?page=${page}`,  // 실제 API 엔드포인트로 수정 필요
        type: 'GET',
        success: function(response) {
            const members = response.members;
            hasMore = response.hasMore;

            members.forEach(member => {
                $('#memberList').append(`
                    <div class="member-item">
                        <input type="checkbox" class="member-checkbox" data-member-id="${member.id}">
                        <div class="member-info">
                            <span><strong>이름:</strong> ${member.name}</span>
                            <span><strong>키:</strong> ${member.height}cm</span>
                            <span><strong>체력:</strong> ${member.stamina}</span>
                            <span><strong>주발:</strong> ${member.mainFoot}</span>
                            <span><strong>주포지션:</strong> ${member.position}</span>
                            <span><strong>나이:</strong> ${member.age}세</span>
                        </div>
                    </div>
                `);
            });
        },
        error: function(xhr, status, error) {
            alert('팀원 목록을 불러오는데 실패했습니다.');
        },
        complete: function() {
            loading = false;
            $('.loading-spinner').hide();
        }
    });
}

// 선택된 팀원 가져오기
function getSelectedMembers() {
    const selectedMembers = [];
    $('.member-checkbox:checked').each(function() {
        selectedMembers.push($(this).data('member-id'));
    });
    return selectedMembers;
}

// 스쿼드 인원 검증
function validateSquadMembers(squadType, selectedMembers) {
    const requiredMembers = parseInt(squadType) * 2;
    if (selectedMembers.length !== requiredMembers) {
        alert(`${squadType}:${squadType} 스쿼드는 총 ${requiredMembers}명이 필요합니다.`);
        return false;
    }
    return true;
}

// 스쿼드 생성
function createSquad(squadType, members) {
    $.ajax({
        url: '/api/squads/create',  // 실제 API 엔드포인트로 수정 필요
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            type: squadType,
            members: members
        }),
        success: function(response) {
            alert('스쿼드가 성공적으로 생성되었습니다.');
            // 스쿼드 생성 후 처리 (예: 스쿼드 페이지로 이동)
        },
        error: function(xhr, status, error) {
            alert('스쿼드 생성에 실패했습니다.');
        }
    });
}