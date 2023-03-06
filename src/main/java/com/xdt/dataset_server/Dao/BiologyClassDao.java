package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.BiologyClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface BiologyClassDao {

    List<BiologyClass> selectAllBioloyClass();

    boolean insertBioloyClass(BiologyClass biologyClass);
}
