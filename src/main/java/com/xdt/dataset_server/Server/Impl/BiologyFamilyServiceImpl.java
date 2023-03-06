package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.BiologyFamilyDao;
import com.xdt.dataset_server.Server.BiologyFamilyService;
import com.xdt.dataset_server.entity.BiologyFamily;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyFamilyServiceImpl
 * @Description: TODO
 * @Date 2023/2/28 10:51
 **/
@Service("BiologyFamilyService")
public class BiologyFamilyServiceImpl implements BiologyFamilyService {
    @Autowired
    private BiologyFamilyDao biologyFamilyDao;

    @Override
    public List<BiologyFamily> selectAllBiologyFamilyByOrderUuid(String orderUuid) {
        System.out.println(orderUuid);
        return biologyFamilyDao.selectAllBiologyFamilyByOrderUuid(orderUuid);
    }

    @Override
    public Page<BiologyFamily> selectAllBiologyFamilyByOrderUuidPagination(String orderUuid, int currentPage, int pageSize) {
        System.out.println(orderUuid);
        Page<BiologyFamily> biologyFamilies = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.biologyFamilyDao.selectAllBiologyFamilyByOrderUuid(orderUuid));
        return biologyFamilies;
    }

    @Override
    public boolean insertBiologyFamily(BiologyFamily biologyFamily) {
        try {
            biologyFamilyDao.insertBiologyFamily(biologyFamily);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
