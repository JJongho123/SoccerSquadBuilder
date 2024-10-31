const express = require('express');
const apiRouter = require('./router/Router'); // .js 확장자는 생략 가능
const cors = require('cors');
const bodyParser = require('body-parser');
const path = require('path');
const {postAxios} = require("../client/axios");

const app = express();

// CORS 설정
app.use(cors());

// JSON 파싱 미들웨어
app.use(bodyParser.json({ limit: '2mb' }));
app.use(bodyParser.urlencoded({ extended: true }));

// 정적 파일 경로 설정
app.use(express.static(path.join(__dirname, '../client')));

// EJS 뷰 엔진 설정
app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "../client/"));

// API 라우터 사용
app.use('/api', apiRouter);

// 기본 페이지 렌더링
app.get('/', (req, res) => {
    res.render("client");
});

// 서버 내보내기
module.exports = app;
