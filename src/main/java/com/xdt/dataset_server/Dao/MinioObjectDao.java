package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.MinioObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MinioObjectDao {
    List<MinioObject> selectAllObjectByBucketName(String bucketName);

    boolean insertMinioObject(MinioObject minioObject);

}
