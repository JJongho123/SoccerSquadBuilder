package com.soccer.com.construct;

import com.soccer.user.mapper.PasswdPolicyMapper;
import com.soccer.user.model.PasswdPolicy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswdPolicyMapper passwdPolciy;

    @PostConstruct
    public void loadData() {
        List<PasswdPolicy> data = passwdPolciy.findAllDatas();

        for (PasswdPolicy passwdPolicy : data) {
            System.out.println("데이터 로드 완료 getPatternName: " + passwdPolicy.getPattern_name());
            System.out.println("데이터 로드 완료 getDescription: " + passwdPolicy.getDescription());
            System.out.println("데이터 로드 완료 getRegexPattern: " + passwdPolicy.getRegex_pattern());
        }

    }
}
