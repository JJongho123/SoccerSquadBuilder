package ssb.soccer.passwdPolicy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.passwdPolicy.mapper.PasswdPolicyMapper;
import ssb.soccer.passwdPolicy.model.PasswdPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PasswdVaildationService {

    private final PasswdPolicyMapper passwdPolciy;
    private Pattern compiledPattern;
    private Map<String, Object> resultMap = null;

    /**
     * 비밀번호 정책 초기화 메서드
     *
     * 이 메서드는 데이터베이스에서 비밀번호 정책 정보를 조회하여
     * 정규식 패턴과 정책 설명 리스트를 생성하고 캐시합니다.
     *
     * 초기화된 데이터가 있을 경우, 재처리 없이 캐시된 데이터( resultMap )를 반환합니다.
     *
     * @return 비밀번호 정책 정보가 담긴 HashMap 객체를 반환합니다.
     *         - `policies`: 활성화된 비밀번호 정책 설명 목록
     *         - `regexPattern`: 모든 정책을 결합한 정규식 패턴
     */
    public Map<String, Object> initPasswdPolicyDatas() {

        // 초기화된 데이터가 있을 경우, 재처리 없이 캐시된 데이터를 반환
        if(resultMap != null){
            return resultMap;
        }

        StringBuilder generateregexPattern = new StringBuilder("^");

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

    public boolean isPolicySatisfaction(String password) {
        if (password == null || compiledPattern == null) {
            return false;
        }
        return compiledPattern.matcher(password).matches();
    }
}

