package ssb.soccer.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.user.mapper.PasswdPolicyMapper;
import ssb.soccer.user.model.PasswdPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PasswdVaildationService {

    private final PasswdPolicyMapper passwdPolciy;
    private Pattern compiledPattern;
    HashMap<String, Object> resultMap = null;

    // 비밀번호 정책 데이터 생성
    public HashMap<String, Object> initPasswdPolicyDatas() {
        StringBuilder generateregexPattern = new StringBuilder("^");

        if(resultMap != null){
            return resultMap;
        }

        resultMap = new HashMap<>();
        List<PasswdPolicy> data = passwdPolciy.findAllDatas();
        List<String> policies = new ArrayList<>();

        for (PasswdPolicy policy : data) {
            String regex = policy.getRegexPattern();
            generateregexPattern.append(regex);

            if (policy.isActive()) {
                policies.add(policy.getDescription());
            }
        }

        generateregexPattern.append("$");
        String regexPattern = generateregexPattern.toString();

        compiledPattern = Pattern.compile(regexPattern);

        resultMap.put("policies", policies);
        resultMap.put("regexPattern", regexPattern);
        return resultMap;
    }

    // 비밀번호 정책 만족여부 확인
    public boolean isPolicySatisfaction(String password) {
        if (password == null || compiledPattern == null) {
            return false;
        }
        return compiledPattern.matcher(password).matches();
    }
}

