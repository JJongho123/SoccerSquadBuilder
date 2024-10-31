const axios = require('axios');

// GET 요청
async function getAxios(url) {
    try {
        return await axios.get(url, {
            headers: {'content-type': 'application/json'}
        });
    } catch (error) {
        console.error(error);
        throw error;
    }
}

// POST 요청
async function postAxios(url, data) {
    try {
        return await axios.post(url, data, {
            headers: {'content-type': 'application/json'}
        });
    } catch (error) {
        console.log(error);
        throw error;
    }
}

// PUT 요청
async function putAxios(url, data) {
    try {
        return await axios.put(url, data, {
            headers: {'Content-Type': 'application/json'},
        });
    } catch (error) {
        console.error(error);
        throw error;
    }
}


// 모듈 내보내기
module.exports = {
    getAxios,
    postAxios,
    putAxios
};
