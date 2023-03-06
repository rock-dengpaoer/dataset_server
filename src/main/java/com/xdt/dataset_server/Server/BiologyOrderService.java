package com.xdt.dataset_server.Server;

import com.xdt.dataset_server.entity.BiologyClass;
import com.xdt.dataset_server.entity.BiologyOrder;
import com.github.pagehelper.Page;

import java.util.List;

public interface BiologyOrderService {
    List<BiologyOrder> selectBiologyOrderByClassUuid(String classUuid);

    boolean insertBiologyOrder(BiologyOrder biologyOrder);

    /*分页查询所有目*/
    Page<BiologyOrder> selectBiologyOrderByClassUuidPagination(String classUuid, int currentPage, int pageSize);
}
