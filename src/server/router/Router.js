const express = require('express');
const Contact = require("../config/dbConnect");
const axios = require("axios"); // .js 확장자는 생략 가능

const router = express.Router();

router.route('/save')
    .get(async (req, res) => {
        let reqData = req.body;
        try {
            const newContact = await Contact.create({
                name: reqData.name || 'example name', // 요청 본문에서 이름을 가져오고 기본값 설정
                email: reqData.email || 'example@example.com', // 요청 본문에서 이메일을 가져오고 기본값 설정
                phone: reqData.phone || '123-456-7890' // 요청 본문에서 전화번호를 가져오고 기본값 설정
            });
            res.status(201).json({ message: "Contact created successfully!", contact: newContact });
        } catch (error) {
            console.error("Error creating contact:", error);
            res.status(500).json({ message: "Error creating contact", error });
        }
    });

router.route('/submit-players')
    .post(async (req, res) => {
        const playersData = req.body.players;
        const playerNames = playersData.split(/\s+/).map(name => name.trim());

        try {
            const players = await Contact.find({ name: { $in: playerNames } }); // 이름 목록을 기준으로 검색
            let GPTResponse = _getGPTResponse(players)
            res.json(players); // 선수 정보 목록을 JSON 형태로 응답
        } catch (error) {
            console.error("선수 목록 조회 오류:", error);
            res.status(500).send("선수 목록 조회 중 오류가 발생했습니다.");
        }
    });

module.exports = router; // CommonJS 방식으로 모듈 내보내기

async function _getGPTResponse(prompt, retryCount = 3) {
    const endpoint = 'https://api.openai.com/v1/chat/completions'
    try {
        const response = await axios.post(endpoint, {
            model: 'gpt-3.5-turbo',
            temperature : 0.5,
            // messages: [{ role: 'user', content: prompt }]
            messages: [{ role: 'user', content: '안녕' }]
        }, {
            headers: {
                'Authorization': `Bearer ${process.env.OPENAI_API_KEY}`,
                'Content-Type': 'application/json',
            },
        });

        console.log('AI Response:', response.data.choices[0].message.content);
    } catch (error) {
        if (error.response && error.response.status === 429 && retryCount > 0) {
            console.log('Rate limit exceeded. Retrying...');
            // 일정 시간 후에 다시 시도 (예: 2초 후)
            await new Promise(resolve => setTimeout(resolve, 7000));
            return _getGPTResponse(prompt, retryCount - 1); // 재시도
        } else {
            console.error('Error fetching AI response:', error);
        }
    }
}

