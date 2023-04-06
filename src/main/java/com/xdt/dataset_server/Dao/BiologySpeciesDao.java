package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.BiologyFamily;
import com.xdt.dataset_server.entity.BiologySpecies;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BiologySpeciesDao {
    List<BiologySpecies> selectAllBiologySpeciesByFamilyUuid(String familyUuid);

    boolean insertBiologySpecies(BiologySpecies biologySpecies);

    /*统计所有种类的个数*/
    Integer CountAllBiologySpecies();

    //通过uuid查询物种信息
    BiologySpecies selectByUuid(String uuid);
}
