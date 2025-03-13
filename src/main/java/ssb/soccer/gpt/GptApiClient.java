package ssb.soccer.gpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.user.model.User;

import java.util.*;

@Service
public class GptApiClient {

    private final OkHttpClient client = new OkHttpClient();

    @Value("${gpt.api.key}")
    private String apiKey;

    public GptApiClient() {
    }

    public String sendMessage(String squadType, List<User> userList) {

        String text = null;

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

        JSONObject messageContent = new JSONObject();
        messageContent.put("role", CommonConstant.GPT_ROLE);
        messageContent.put("content", generatePrompt(squadType, playerArray));

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", CommonConstant.GPT_MODEL);
        requestBody.put("messages", new JSONArray().put(messageContent));
        requestBody.put("max_tokens", CommonConstant.MAX_TOKENS);
        requestBody.put("temperature", CommonConstant.TEMPERATURE);
        requestBody.put("top_p", CommonConstant.TOP_P);

        Request request = new Request.Builder()
                .url(CommonConstant.API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(requestBody.toString(), MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root;

            root = mapper.readTree(response.body().string());
            JsonNode choicesNode = root.get("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode choice = choicesNode.get(0);
                JsonNode message = choice.get("message");
                text = message.get("content").toString();
            }
        } catch (Exception e) {
            throw new CustomApiException(ExceptionEnum.GPT_RESPONSE_FAILED, e.getMessage());
        }
        return text;

    }

    // GPT 요청 prompt 메시지
    private StringBuilder generatePrompt(String squadType, JSONArray playerArray){

        StringBuilder prompt = new StringBuilder();
        prompt.append("다음 선수들을 균형 잡힌 팀으로 나눠줘. ")
                .append("팀은 '1팀'과 '2팀'으로 나누고, ")
                .append("경기 방식(예: 5:5)에 맞춰 각 팀의 선수가 동일하도록 조정하되, ")
                .append("남는 선수는 벤치(후보)로 배치해줘.\n\n")

                .append("경기 방식: ").append(squadType).append("\n\n")

                .append("경기 방식에 맞게 팀을 균형있게 나눠줘.\n\n")

                .append("**팀 균형 조건:**\n")
                .append("- 각 팀은 **FW(공격수), MF(미드필더), DF(수비수)가 균형 있게 포함**되어야 함\n")
                .append("- 키(Height), 체력(Stamina), 나이(Age) 등 **각 팀의 평균 능력치가 비슷하도록 배치**\n")
                .append("- 오른발잡이(R), 왼발잡이(L), 양발잡이(B)의 **비율도 팀 간 균형을 맞춰 배치**\n")
                .append("- 특정 팀에 체력이 높은 선수만 몰리거나, 나이 많은 선수만 몰리지 않도록 조정\n")
                .append("- 특정 팀에 FW(공격수)나 DF(수비수)가 너무 몰리지 않도록 조정\n")
                .append("- 경기 방식에 따라 각 팀의 선수 수를 동일하게 맞추되, 남는 선수는 후보로 분류해줘.\n")
                .append("- 후보 명단도 팀 균형을 고려하여 배치할 수 있도록 구성해줘.\n\n")

                .append("**선수 목록:**\n").append(playerArray.toString()).append("\n\n")

                .append("**출력 형식 (팀별로 능력치 평균 포함, 정규식 처리 가능하도록 구조화)**\n")
                .append("[1팀]\n")
                .append("1. 선수이름 (포지션: XX)\n")
                .append("2. 선수이름 (포지션: XX)\n")
                .append("...\n\n")

                .append("[2팀]\n")
                .append("1. 선수이름 (포지션: XX)\n")
                .append("2. 선수이름 (포지션: XX)\n")
                .append("...\n\n")

                .append("[후보]\n")
                .append("1. 선수이름 (포지션: XX)\n");

        return prompt;
    }
}
