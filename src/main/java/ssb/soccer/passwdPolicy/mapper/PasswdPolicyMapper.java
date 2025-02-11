package ssb.soccer.passwdPolicy.mapper;

import ssb.soccer.passwdPolicy.model.PasswdPolicy;

import java.util.List;

public interface PasswdPolicyMapper {
    List<PasswdPolicy> findAllDatas();
}