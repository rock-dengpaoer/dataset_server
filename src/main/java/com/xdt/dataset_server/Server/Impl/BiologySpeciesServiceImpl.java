package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.BiologySpeciesDao;
import com.xdt.dataset_server.Server.BiologySpeciesService;
import com.xdt.dataset_server.entity.BiologySpecies;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologySpeciesImpl
 * @Description: TODO
 * @Date 2023/2/28 10:59
 **/
@Service("BiologySpeciesService")
public class BiologySpeciesServiceImpl implements BiologySpeciesService {

    @Autowired
    private BiologySpeciesDao biologySpeciesDao;

    @Override
    public List<BiologySpecies> selectAllBiologySpeciesByFamilyUuid(String familyUuid) {
        return this.biologySpeciesDao.selectAllBiologySpeciesByFamilyUuid(familyUuid);
    }

    @Override
    public Page<BiologySpecies> selectAllBiologySpeciesByFamilyUuidPagination(String familyUuid, int currentPage, int pageSize) {
        System.out.println(familyUuid);
        Page<BiologySpecies> biologySpecies = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.biologySpeciesDao.selectAllBiologySpeciesByFamilyUuid(familyUuid));
        return biologySpecies;
    }

    @Override
    public boolean insertBiologySpecies(BiologySpecies biologySpecies) {
        try {
            biologySpeciesDao.insertBiologySpecies(biologySpecies);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Integer CountAllBiologySpecies() {
        return biologySpeciesDao.CountAllBiologySpecies();
    }
}
