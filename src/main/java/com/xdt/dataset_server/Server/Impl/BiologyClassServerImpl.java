package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.BiologyClassDao;
import com.xdt.dataset_server.Server.BiologyClassService;
import com.xdt.dataset_server.entity.BiologyClass;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyClassServerImpl
 * @Description: TODO
 * @Date 2023/2/27 20:17
 **/
@Service("BiologyClassService")
public class BiologyClassServerImpl implements BiologyClassService {

    @Autowired
    private BiologyClassDao biologyClassDao;

    @Override
    public List<BiologyClass> selectAllBioloyClass() {
        return biologyClassDao.selectAllBioloyClass();
    }

    @Override
    public boolean insertBioloyClass(BiologyClass biologyClass) {
        try {
            biologyClassDao.insertBioloyClass(biologyClass);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public Page<BiologyClass> selectAllBiologyClassPagination(int currentPage, int pageSize) {
        Page<BiologyClass> biologyClasses = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.biologyClassDao.selectAllBioloyClass());
        return biologyClasses;
    }
}
