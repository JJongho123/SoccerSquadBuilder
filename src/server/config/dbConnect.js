const mongoose = require("mongoose");
const dotenv = require("dotenv");

// 환경 변수 로드
dotenv.config();

// DB 연결 함수
const dbConnect = async () => {
    try {
        await mongoose.connect(process.env.DB_CONNECT, {});
        console.log("DB Connected !!");
    } catch (err) {
        console.error("DB Connection Error:", err);
    }
};

// 데이터베이스 연결 호출
dbConnect();

// 연락처 모델 정의 (스키마 없이)
const Contact = mongoose.model("Contact", new mongoose.Schema({}, {strict: false}));

module.exports = Contact;
