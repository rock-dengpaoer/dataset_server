package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.BiologyOrderDao;
import com.xdt.dataset_server.Server.BiologyOrderService;
import com.xdt.dataset_server.entity.BiologyClass;
import com.xdt.dataset_server.entity.BiologyOrder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyOrderDao
 * @Description: TODO
 * @Date 2023/2/28 10:31
 **/
@Service("BiologyOrderService")
public class BiologyOrderServiceImpl implements BiologyOrderService {

    @Autowired
    private BiologyOrderDao biologyOrderDao;

    @Override
    public List<BiologyOrder> selectBiologyOrderByClassUuid(String classUuid) {
        return biologyOrderDao.selectBiologyOrderByClassUuid(classUuid);
    }

    @Override
    public boolean insertBiologyOrder(BiologyOrder biologyOrder) {
        try {
            biologyOrderDao.insertBiologyOrder(biologyOrder);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Page<BiologyOrder> selectBiologyOrderByClassUuidPagination(String classUuid, int currentPage, int pageSize) {
        System.out.println("classUuid is "  + classUuid);
        Page<BiologyOrder> biologyOrders = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.biologyOrderDao.selectBiologyOrderByClassUuid(classUuid));
        return biologyOrders;
    }


}
