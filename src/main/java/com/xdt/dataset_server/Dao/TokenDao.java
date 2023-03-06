package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.TokenInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TokenDao {

    boolean insertToken(TokenInfo tokenInfo);
}
