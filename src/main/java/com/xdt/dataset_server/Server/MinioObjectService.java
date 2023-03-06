package com.xdt.dataset_server.Server;

import com.github.pagehelper.Page;
import com.xdt.dataset_server.entity.MinioObject;

import java.util.List;

public interface MinioObjectService {
    List<MinioObject> selectAllObjectByBucketName(String bucketName);

    boolean insertMinioObject(MinioObject minioObject);

    /*分页查询所有图片*/
    Page<MinioObject> selectAllObjectByBucketNamePagination(String bucketName, int currentPage, int pageSize);

}
