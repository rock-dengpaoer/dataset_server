package com.xdt.dataset_server.Server;


import com.xdt.dataset_server.entity.BiologyFamily;
import com.github.pagehelper.Page;

import java.util.List;

public interface BiologyFamilyService {
    List<BiologyFamily> selectAllBiologyFamilyByOrderUuid(String orderUuid);

    /*分页查询所有目*/
    Page<BiologyFamily> selectAllBiologyFamilyByOrderUuidPagination(String orderUuid, int currentPage, int pageSize);

    boolean insertBiologyFamily(BiologyFamily biologyFamily);
}
