package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.BiologyClass;
import com.xdt.dataset_server.entity.BiologyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BiologyOrderDao {
    List<BiologyOrder> selectBiologyOrderByClassUuid(String classUuid);

    boolean insertBiologyOrder(BiologyOrder biologyOrder);
}
