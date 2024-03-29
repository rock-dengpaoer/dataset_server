package com.xdt.dataset_server.Server.small.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdt.dataset_server.Dao.small.AnimalObjectDao;
import com.xdt.dataset_server.Server.small.AnimalObjectService;
import com.xdt.dataset_server.entity.BiologyClass;
import com.xdt.dataset_server.entity.small.AnimalObjectInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalObjectServiceImpl implements AnimalObjectService {

    private final AnimalObjectDao animalObjectDao;

    public AnimalObjectServiceImpl(AnimalObjectDao animalObjectDao) {
        this.animalObjectDao = animalObjectDao;
    }

    @Override
    public boolean insert(AnimalObjectInfo animalObjectInfo) {
        return this.animalObjectDao.insert(animalObjectInfo);
    }

    @Override
    public boolean del(String uuid) {
        return this.animalObjectDao.del(uuid);
    }

    @Override
    public boolean delByName(String name) {
        return this.animalObjectDao.delByName(name);
    }

    @Override
    public List<AnimalObjectInfo> selectByName(String name) {
        return this.animalObjectDao.selectByName(name);
    }

    @Override
    public List<AnimalObjectInfo> selectByBucketName(String bucketName) {
        return this.animalObjectDao.selectByBucketName(bucketName);
    }

    @Override
    public Page<AnimalObjectInfo> selectByBucketNamePagination(int currentPage, int pageSize, String bucketName) {
        Page<AnimalObjectInfo> animalObjectInfos = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.animalObjectDao.selectByBucketName(bucketName));
        return animalObjectInfos;
    }

    @Override
    public AnimalObjectInfo select(String uuid) {
        return this.animalObjectDao.select(uuid);
    }

    @Override
    public boolean update(AnimalObjectInfo animalObjectInfo) {
        return this.animalObjectDao.update(animalObjectInfo);
    }

    @Override
    public Integer countAll() {
        return this.animalObjectDao.countAll();
    }

    @Override
    public Integer countByUser(String userUuid) {
        return this.animalObjectDao.countByUser(userUuid);
    }

    @Override
    public Integer countByBucket(String bucketName) {
        return this.animalObjectDao.countByBucket(bucketName);
    }
}
