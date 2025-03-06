import {getUserData} from "./util/userUtil.js";

let role;
let page = 1;
let loading = false;
let hasMore = true;
let teamId;

$(document).ready(async function () {
    // 초기 데이터 로드
    const userData = await getUserData();
    teamId = userData.teamId
    role = userData.role;
    loadTeamInfo(parseInt(userData.teamId));

    // 이벤트 리스너 등록
    initializeEventListeners();
});

// 모든 이벤트 리스너 초기화
function initializeEventListeners() {

    // 전체 선택 체크박스 이벤트
    $('#selectAllMembers').on('change', function() {
        const isChecked = $(this).prop('checked');
        $('.member-checkbox').prop('checked', isChecked);
    });

    // 개별 체크박스 변경 시 전체 선택 체크박스 상태 업데이트
    $(document).on('change', '.member-checkbox', function() {
        updateSelectAllCheckbox();
    });

    // 스쿼드 유형 선택 이벤트
    $('#squadType').on('change', function () {
        $('#createSquadBtn').prop('disabled', !$(this).val());
    });

    // 스쿼드 생성 버튼 클릭
    $('#createSquadBtn').on('click', function () {
        const squadType = $('#squadType').val();
        const selectedMembers = getSelectedMembers();
        // if (validateSquadMembers(squadType, selectedMembers)) {
            createSquad(squadType, selectedMembers);
        // }
    });

    // 팀 수정 버튼 클릭
    $('.edit-team-btn').on('click', function () {
        if (role !== '팀장') {
            alert('팀장만 수정할 수 있습니다.');
            return;
        }
        openTeamEditPopup();
    });

    // 팀원 팝업 닫기 버튼 클릭
    $('#member-update-cancel').on('click', function () {
        closeMemberEditPopup();
    });

    // 팀 팝업 닫기 버튼 클릭
    $('#team-popup-btn').on('click', function () {
        closeTeamEditPopup();
    });

    // 팀원 수정 버튼 클릭 (동적 요소)
    $(document).on('click', '.edit-member-btn', function () {
        if (role !== '팀장') {
            alert('팀장만 수정할 수 있습니다.');
            return;
        }
        const memberId = $(this).data('member-id');
        openMemberEditPopup(parseInt(memberId));
    });

    // 팀원 추방 버튼 클릭 (동적 요소)
    $(document).on('click', '.remove-member-btn', function () {
        if (role !== '팀장') {
            alert('팀장만 추방할 수 있습니다.');
            return;
        }
        const memberId = $(this).data('member-id');
        removeMember(memberId);
    });

    // 팀 정보 수정 폼 제출
    $('#teamEditForm').on('submit', function (e) {
        e.preventDefault();
        updateTeamInfo();
    });

    // 팀원 정보 수정 폼 제출
    $('#memberEditForm').on('submit', function (e) {
        e.preventDefault();
        updateMemberInfo();
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
}

// 팀 정보 로드
function loadTeamInfo(teamId) {
    $.ajax({
        url: `/api/team/${teamId}`,
        type: 'GET',
        success: function (ajaxData) {
            const team = ajaxData.data.teamDetail;
            const userList = ajaxData.data.userList;

            updateTeamInfoDisplay(team, userList.length);
            renderMemberList(userList);
        },
        error: function (xhr, status, error) {
            console.error('팀 정보 로드 실패:', error);
            alert('팀 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 팀 정보 화면 업데이트
function updateTeamInfoDisplay(team, currentMembers) {
    $('#team-name').text(team.teamName);
    $('#totalMembers').text(team.teamMemberMaxCount);
    $('#currentMembers').text(currentMembers);
    $('#location').text(team.teamActivityArea);
    $('#teamLevel').text(team.teamLevel);
    $('#leaderName').text(team.name);
}

// 팀원 목록 렌더링
function renderMemberList(userList) {
    const memberList = $('#memberList');
    memberList.empty();

    userList.forEach(member => {
        const memberActions = role === '팀장' ? `
            <div class="member-actions">
                <button id="edit-member-btn" class="edit-member-btn" data-member-id="${member.id}">수정</button>
                <button class="remove-member-btn" data-member-id="${member.id}">추방</button>
            </div>
        ` : '';

        memberList.append(`
            <div class="member-item">
                <input type="checkbox" class="member-checkbox" data-member-id="${member.id}">
                <div class="member-info">
                    <span><strong>이름:</strong> ${member.name}</span>
                    <span><strong>키:</strong> ${member.height}cm</span>
                    <span><strong>체력:</strong> ${member.stamina}</span>
                    <span><strong>주발:</strong> ${member.mainFoot || ""}</span>
                    <span><strong>주포지션:</strong> ${member.position || ""}</span>
                    <span><strong>나이:</strong> ${member.age}세</span>
                </div>
                ${memberActions}
            </div>
        `);
    });
}

// 팀 정보 수정 팝업 열기
function openTeamEditPopup() {

    $('#editTeamName').val($('#team-name').text());
    $('#editMaxMembers').val($('#totalMembers').text());
    $('#editTeamLevel').val($('#teamLevel').text());
    $('#editLocation').val($('#location').text());
    $('#teamEditPopup').fadeIn(300);
}

// 팀 정보 수정
function updateTeamInfo() {
    const formData = {
        teamName: $('#editTeamName').val(),
        maxMembers: $('#editMaxMembers').val(),
        teamLevel: $('#editTeamLevel').val(),
        location: $('#editLocation').val()
    };

    $.ajax({
        url: '/api/teams/update',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (response) {
            alert('팀 정보가 수정되었습니다.');
            closeTeamEditPopup();
            location.reload();
        },
        error: function (xhr) {
            alert('팀 정보 수정에 실패했습니다.');
        }
    });
}

// 팀원 정보 수정 팝업 열기
function openMemberEditPopup(memberId) {
    $.ajax({
        url: `/api/user/${memberId}`,
        type: 'GET',
        success: function (ajaxData) {
            const member = ajaxData.data
            $('#editMemberId').val(memberId);
            $('#editMemberPosition').val(member.position);
            $('#editMemberStamina').val(member.stamina);
            $('#editMemberAge').val(member.age);
            $('#editMemberMainFoot').val(member.mainFoot);
            $('#editMemberHeight').val(member.height);
            $('#memberEditPopup').fadeIn(300);
        },
        error: function (xhr) {
            alert('팀원 정보를 불러오는데 실패했습니다.');
        }
    });
}

// 팀원 정보 수정
function updateMemberInfo() {
    const memberId = $('#editMemberId').val();
    const formData = {
        position: $('#editMemberPosition').val(),
        stamina: $('#editMemberStamina').val(),
        age: $('#editMemberAge').val(),
        mainFoot: $('#editMemberMainFoot').val(),
        height: $('#editMemberHeight').val(),
        id : memberId
    };

    $.ajax({
        url: `/api/user`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function (response) {
            alert('팀원 정보가 수정되었습니다.');
            closeMemberEditPopup();
            loadTeamInfo(parseInt(teamId));
        },
        error: function (xhr) {
            alert('팀원 정보 수정에 실패했습니다.');
        }
    });
}

// 팀원 추방
function removeMember(memberId) {
    if (!confirm('정말로 이 팀원을 추방하시겠습니까?')) {
        return;
    }

    $.ajax({
        url: `/api/members/${memberId}/remove`,
        type: 'DELETE',
        success: function (response) {
            alert('팀원이 추방되었습니다.');
            loadTeamInfo(parseInt(userData.teamId));
        },
        error: function (xhr) {
            alert('팀원 추방에 실패했습니다.');
        }
    });
}

// 팝업 닫기 함수들
function closeTeamEditPopup() {
    $('#teamEditPopup').fadeOut(300);
}

function closeMemberEditPopup() {
    $('#memberEditPopup').fadeOut(300);
}

// 스쿼드 관련 함수들
function getSelectedMembers() {
    const selectedMembers = [];
    $('.member-checkbox:checked').each(function () {
        selectedMembers.push($(this).data('member-id'));
    });
    return selectedMembers;
}

function validateSquadMembers(squadType, selectedMembers) {
    const requiredMembers = parseInt(squadType) * 2;
    if (selectedMembers.length !== requiredMembers) {
        alert(`${squadType}:${squadType} 스쿼드는 총 ${requiredMembers}명이 필요합니다.`);
        return false;
    }
    return true;
}

function createSquad(squadType, members) {
    $.ajax({
        url: '/api/squads',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            squadType: squadType,
            memberIds: members
        }),
        success: function (response) {
            alert('스쿼드가 성공적으로 생성되었습니다.');
        },
        error: function (xhr, status, error) {
            alert('스쿼드 생성에 실패했습니다.');
        }
    });
}

// 전체 선택 체크박스 상태 업데이트
function updateSelectAllCheckbox() {
    const totalCheckboxes = $('.member-checkbox:not(#selectAllMembers)').length;
    const checkedCheckboxes = $('.member-checkbox:not(#selectAllMembers):checked').length;

    $('#selectAllMembers').prop({
        checked: totalCheckboxes === checkedCheckboxes,
        indeterminate: checkedCheckboxes > 0 && checkedCheckboxes < totalCheckboxes
    });
}