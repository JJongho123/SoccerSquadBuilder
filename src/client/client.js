$(document).ready(function () {
    $('#submitBtn').on("click", async () => {
        const url = '/api/submit-players'; // 서버 주소 수정

        const playerData = { players: $('#players').val() }; // 줄바꿈으로 분리

        try {
            const result = await axios.post(url, playerData, {
                headers: { 'Content-Type': 'application/json' }
            });

            const redPlayersContainer = $('#redPlayersContainer');
            const greenPlayersContainer = $('#greenPlayersContainer');

            redPlayersContainer.empty();
            greenPlayersContainer.empty();

            // 선수들을 빨간 팀과 초록 팀으로 나누기
            result.data.forEach((player, index) => {
                if (index % 2 === 0) {
                    redPlayersContainer.append(`<div class="red-box">${player.name}</div>`); // 빨간 팀 선수 추가
                } else {
                    greenPlayersContainer.append(`<div class="green-box">${player.name}</div>`); // 초록 팀 선수 추가
                }
            });
        } catch (error) {
            console.error("Error submitting players:", error);
        }
    });
});
