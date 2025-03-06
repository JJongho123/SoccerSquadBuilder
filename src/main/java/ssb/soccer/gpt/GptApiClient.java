package ssb.soccer.gpt;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.user.model.User;

import java.io.IOException;
import java.util.List;

public class GptApiClient {

    private final OkHttpClient client = new OkHttpClient();

    public String sendMessage(String squadType, List<User> userList) throws IOException {
        JSONArray playerArray = new JSONArray();
        for (User player : userList) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("name", player.getName());
            playerJson.put("position", player.getPosition());
            playerJson.put("age", player.getAge());
            playerJson.put("height", player.getHeight());
            playerJson.put("mainFoot", player.getMainFoot());
            playerArray.put(playerJson);
        }

        // GPT 요청 메시지
        String prompt = "다음 선수들을 균형 잡힌 팀으로 나눠줘. " +
                "팀은 '빨간팀'과 '녹색팀'으로 나누고, " +
                "남는 선수는 후보로 배치해줘.\n\n" +
                "경기 방식: " + squadType + "\n\n" +
                "선수 목록: " + playerArray.toString();

        JSONObject messageContent = new JSONObject();
        messageContent.put("role", "user");
        messageContent.put("content", prompt);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new JSONArray().put(messageContent));
        requestBody.put("max_tokens", 300);

        Request request = new Request.Builder()
                .url(CommonConstant.API_URL)
                .header("Authorization", "Bearer " + CommonConstant.API_KEY)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}
