package ssb.soccer.user.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.user.mapper.PasswdPolicyMapper;
import ssb.soccer.user.model.PasswdPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PasswdVaildationService {

    private final PasswdPolicyMapper passwdPolciy;

    public HashMap<String, Object> initPasswdPolicyDatas() {

        HashMap<String, Object> resultMap = new HashMap<>();

        List<PasswdPolicy> data = passwdPolciy.findAllDatas();

        List<String> policies = new ArrayList<>();
        List<String> regexParttern = new ArrayList<>();

        String policyDescription;

        for (PasswdPolicy passwdPolicy : data) {

            String regex = passwdPolicy.getRegex_pattern();
            regexParttern.add(regex);

            // 특수문자 공백 조건
            Pattern specialCharPattern = Pattern.compile("\\[([^]]+)]"); // 특수문자 그룹 찾기
            Matcher specialCharMatcher = specialCharPattern.matcher(regex);
            if (specialCharMatcher.find()) {
                String specialChars = specialCharMatcher.group(1);
                policies.add("\n비밀번호에는 다음 특수문자 중 하나 이상이 포함되어야 합니다: " + specialChars);
                policies.add("\n비밀번호에는 공백이 포함될 수 없습니다.");
            }

            // 길이 제한 조건
            Pattern lengthPattern = Pattern.compile("\\{(\\d+),(\\d+)}"); // {min,max} 찾기
            Matcher lengthMatcher = lengthPattern.matcher(regex);
            if (lengthMatcher.find()) {
                int minLength = Integer.parseInt(lengthMatcher.group(1));
                int maxLength = Integer.parseInt(lengthMatcher.group(2));
                policies.add("\n비밀번호는 " + minLength + "자 이상 " + maxLength + "자 이하여야 합니다.");
            }
        }

        policyDescription = "비밀번호 정책: " + String.join("\n", policies);
        System.out.println(policyDescription);

        resultMap.put("policies", policies);
        resultMap.put("regexParttern", regexParttern);

        return resultMap;
    }
}
