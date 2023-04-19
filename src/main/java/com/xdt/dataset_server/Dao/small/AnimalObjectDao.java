package com.xdt.dataset_server.Dao.small;

import com.xdt.dataset_server.entity.small.AnimalObjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnimalObjectDao {

    //添加
    boolean insert(AnimalObjectInfo animalObjectInfo);

    //删除
    boolean del(String uuid);

    //查找-模糊查找 通过name
    List<AnimalObjectInfo> selectByName(String name);

    //查找 通过bucket_name
    List<AnimalObjectInfo> selectByBucketName(String bucketName);

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
