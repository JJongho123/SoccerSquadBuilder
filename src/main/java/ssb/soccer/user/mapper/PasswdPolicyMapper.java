package ssb.soccer.user.mapper;

import ssb.soccer.user.model.PasswdPolicy;

import java.util.List;

public interface PasswdPolicyMapper {
    List<PasswdPolicy> findAllDatas();
}