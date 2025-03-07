package ssb.soccer.com.constant;

public class CommonConstant {
    // session, cookie 만료 시간 (초)
    public final static int EXPIRY_DURATION_SECONDS = 3600;

    // Redis Hashmap 사용자 정보 key
    public final static String USER_KEY = "user";

    // GPT 관련 상수
    public final static String API_URL = "https://api.openai.com/v1/chat/completions";
    public final static String API_KEY = "";
    public final static String GPT_MODEL = "gpt-3.5-turbo";
    public final static String GPT_ROLE = "user";
    public final static int MAX_TOKENS = 1000;
    public final static double TEMPERATURE = 0.3;
    public final static double TOP_P = 0.3;

}
