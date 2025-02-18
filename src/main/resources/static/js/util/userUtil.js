export async function getUserData() {
    try {
        const ajaxData = await $.ajax({
            url: `/api/user/data`,
            type: 'get'
        });

        if (ajaxData.data.role) {
            $(".create-team-btn").remove();
        }

        return ajaxData.data; // ✅ userData를 정상적으로 반환
    } catch (error) {
        alert(error);
        return null; // 오류 발생 시 null 반환
    }
}
