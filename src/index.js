const app = require('./server/app'); // .js 확장자는 생략 가능
const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
