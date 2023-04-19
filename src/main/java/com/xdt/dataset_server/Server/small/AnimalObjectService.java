package com.xdt.dataset_server.Server.small;

import com.github.pagehelper.Page;
import com.xdt.dataset_server.entity.small.AnimalObjectInfo;

import java.util.List;

public interface AnimalObjectService {
    //添加
    boolean insert(AnimalObjectInfo animalObjectInfo);

    //删除
    boolean del(String uuid);

    //查找-模糊查找 通过name
    List<AnimalObjectInfo> selectByName(String name);

    //查找 通过bucket_name
    List<AnimalObjectInfo> selectByBucketName(String bucketName);

    //分页查找 通过bucket_name
    Page<AnimalObjectInfo> selectByBucketNamePagination(int currentPage, int pageSize, String bucketName);

    //查找，通过uuid
    AnimalObjectInfo select(String uuid);

    //更新
    boolean update(AnimalObjectInfo animalObjectInfo);

    //统计总个数
    Integer countAll();

    //统计用户上传的数量
    Integer countByUser(String userUuid);

    //统计bucket内图片的数量
    Integer countByBucket(String bucketName);
}
