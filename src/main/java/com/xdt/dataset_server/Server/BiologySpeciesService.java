package com.xdt.dataset_server.Server;

import com.xdt.dataset_server.entity.BiologySpecies;
import com.github.pagehelper.Page;

import java.util.List;

public interface BiologySpeciesService {
    List<BiologySpecies> selectAllBiologySpeciesByFamilyUuid(String familyUuid);

    /*分页查询所有种*/
    Page<BiologySpecies> selectAllBiologySpeciesByFamilyUuidPagination(String familyUuid, int currentPage, int pageSize);

    boolean insertBiologySpecies(BiologySpecies biologySpecies);

    /*统计所有种类的个数*/
    Integer CountAllBiologySpecies();

    //通过uuid查询物种信息
    BiologySpecies selectByUuid(String uuid);
}
