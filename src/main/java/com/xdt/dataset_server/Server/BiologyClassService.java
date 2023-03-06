package com.xdt.dataset_server.Server;

import com.xdt.dataset_server.entity.BiologyClass;
import com.github.pagehelper.Page;

import java.util.List;

public interface BiologyClassService {
    List<BiologyClass> selectAllBioloyClass();

    boolean insertBioloyClass(BiologyClass biologyClass);

    /*分页查询所有纲*/
    Page<BiologyClass> selectAllBiologyClassPagination(int currentPage, int pageSize);
}
