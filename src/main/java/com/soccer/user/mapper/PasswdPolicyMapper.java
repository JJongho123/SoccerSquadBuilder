package com.soccer.user.mapper;

import com.soccer.user.model.PasswdPolicy;

import java.util.List;

public interface PasswdPolicyMapper {
    List<PasswdPolicy> findAllDatas();
}