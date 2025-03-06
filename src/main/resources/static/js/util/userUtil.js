export async function getUserData() {
    try {
        const ajaxData = await $.ajax({
            url: `/api/user/info`,
            type: 'get'
        });

        return ajaxData.data; // userData를 정상적으로 반환
    } catch (error) {
        alert(error);
        return null; // 오류 발생 시 null 반환
    }
}
