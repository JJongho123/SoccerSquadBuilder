//package ssb.soccer.com.construct;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import ssb.soccer.user.mapper.PasswdPolicyMapper;
//import ssb.soccer.user.model.PasswdPolicy;
//
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer {
//
//    private final PasswdPolicyMapper passwdPolciy;
//
//    @PostConstruct
//    public void loadData() {
//        List<PasswdPolicy> data = passwdPolciy.findAllDatas();
//
//        for (PasswdPolicy passwdPolicy : data) {
//
//            String regex = passwdPolicy.getRegex_pattern();
//
//            // 특수문자 추출을 위한 정규식
//            Pattern specialCharPattern = Pattern.compile("[^\\w\\s]");
//            Matcher matcher = specialCharPattern.matcher(regex);
//
//            // 추출된 특수문자를 저장할 Set (중복 방지)
//            Set<Character> specialChars = new LinkedHashSet<>();
//            while (matcher.find()) {
//                specialChars.add(matcher.group().charAt(0));
//            }
//
//            // 결과 출력
//            System.out.println("허용된 특수문자: " + specialChars);
//
//            // 사용자에게 보여줄 설명 생성
//            if (!specialChars.isEmpty()) {
//                System.out.println("비밀번호는 다음 특수문자만 가능합니다: " + specialChars);
//            } else {
//                System.out.println("특수문자가 없습니다.");
//            }
//
//            System.out.println("데이터 로드 완료 getPatternName: " + passwdPolicy.getPattern_name());
//            System.out.println("데이터 로드 완료 getDescription: " + passwdPolicy.getDescription());
//            System.out.println("데이터 로드 완료 getRegexPattern: " + passwdPolicy.getRegex_pattern());
//        }
//
//    }
//}
