package com.soccer.user.mappers;

import com.soccer.user.models.PasswdPolicy;

import java.util.List;

public interface PasswdPolicyMapper {
    List<PasswdPolicy> findAllDatas();
}