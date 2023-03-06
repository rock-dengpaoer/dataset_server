package com.xdt.dataset_server.Server.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdt.dataset_server.Dao.MinioObjectDao;
import com.xdt.dataset_server.Server.MinioObjectService;
import com.xdt.dataset_server.entity.MinioObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName MinioObjectServiceImpl
 * @Description: TODO
 * @Date 2023/2/28 11:34
 **/
@Service("MinioObjectService")
@Slf4j
public class MinioObjectServiceImpl implements MinioObjectService {

    @Autowired
    private MinioObjectDao minioObjectDao;

    @Override
    public List<MinioObject> selectAllObjectByBucketName(String bucketName) {
        return this.minioObjectDao.selectAllObjectByBucketName(bucketName);
    }

    @Override
    public boolean insertMinioObject(MinioObject minioObject) {
        try {
            this.minioObjectDao.insertMinioObject(minioObject);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Page<MinioObject> selectAllObjectByBucketNamePagination(String bucketName, int currentPage, int pageSize) {
        System.out.println(bucketName);
        Page<MinioObject> minioObjects = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.minioObjectDao.selectAllObjectByBucketName(bucketName));
        return minioObjects;
    }
}
