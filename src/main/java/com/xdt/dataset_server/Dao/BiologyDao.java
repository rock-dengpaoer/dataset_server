package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.BiologyAllName;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BiologyDao {
    BiologyAllName getAllNameBySpeciesUuid(String uuid);
}
