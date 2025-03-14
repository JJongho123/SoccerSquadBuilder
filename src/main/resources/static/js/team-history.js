import {getUserData} from "./util/userUtil.js";

let page = 1;
let loading = false;
let hasMore = true;
let currentSort = 'date';
let searchTerm = '';
let teamId;
let filters = {
    formation: '',
    date: ''
};

// 팀 목록을 가져오는 함수
function fetchTeamHistory(reset = false) {
    if (loading || (!hasMore && !reset)) return;

    if (reset) {
        page = 1;
        hasMore = true;
        $('#teamList').empty();
    }

    loading = true;
    $('#loadingIndicator').show();

    $.ajax({
        url: `/api/history/${teamId}`,
        method: 'GET',
        success: function(response) {
            $('#loadingIndicator').hide();

            if (response.teams.length === 0) {
                hasMore = false;
                if (page === 1) {
                    if (searchTerm || filters.formation || filters.date) {
                        $('#noResults').show();
                        $('#teamList').hide();
                    } else {
                        $('#teamList').html('<div class="no-data">생성된 팀이 없습니다.</div>');
                        $('#noResults').hide();
                    }
                }
                return;
            }

            $('#noResults').hide();
            $('#teamList').show();
            renderTeams(response.data);
            page++;
            loading = false;
        },
        error: function(error) {
            console.error('Error fetching team history:', error);
            $('#loadingIndicator').hide();
            loading = false;
        }
    });
}

// 팀 목록을 화면에 렌더링하는 함수
function renderTeams(teams) {
    $('#teamCount').text(teams.length);
    const teamList = teams.map(team => `
        <div class="team-item" data-team-id="${team.id}">
            <div class="team-title">${team.title}</div>
            <div class="team-info">
                <span class="team-type">${team.squadType}</span>
                <span class="team-date">${team.createdAt}</span>
            </div>
        </div>
    `).join('');

    if (page === 1) {
        $('#teamList').html(teamList);
    } else {
        $('#teamList').append(teamList);
    }
}


// 검색 및 필터 적용 함수
function applyFilters() {
    fetchTeamHistory(true);
}

// 이벤트 리스너 등록
$(document).ready(async function () {

    const userData = await getUserData();
    teamId = userData.teamId

    // 초기 데이터 로드
    fetchTeamHistory();

    // 검색 이벤트
    let searchTimeout;
    $('#searchInput').on('input', function () {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            searchTerm = $(this).val().trim();
            applyFilters();
        }, 300);
    });

    // 필터 변경 이벤트
    $('.filter-select').on('change', function () {
        const filterType = $(this).attr('id').replace('Filter', '');
        filters[filterType] = $(this).val();
        applyFilters();
    });

    // 정렬 버튼 클릭 이벤트
    $('.sort-btn').on('click', function () {
        $('.sort-btn').removeClass('active');
        $(this).addClass('active');
        currentSort = $(this).data('sort');
        applyFilters();
    });

    // 필터 초기화 버튼
    $('#resetFilters').on('click', function () {
        $('#searchInput').val('');
        $('.filter-select').val('');
        searchTerm = '';
        filters = {
            formation: '',
            date: ''
        };
        applyFilters();
    });

    // 검색 초기화 버튼
    $('#clearSearch').on('click', function () {
        $('#searchInput').val('');
        searchTerm = '';
        applyFilters();
    });

    // 무한 스크롤
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            fetchTeamHistory();
        }
    });

    // 팀 아이템 클릭 이벤트
    $(document).on('click', '.team-item', function () {
        const teamId = $(this).data('team-id');
        showTeamDetail(teamId);
    });
});

