package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.BiologyFamily;
import com.xdt.dataset_server.entity.BiologyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BiologyFamilyDao {

    List<BiologyFamily> selectAllBiologyFamilyByOrderUuid(String orderUuid);

    boolean insertBiologyFamily(BiologyFamily biologyFamily);
}
