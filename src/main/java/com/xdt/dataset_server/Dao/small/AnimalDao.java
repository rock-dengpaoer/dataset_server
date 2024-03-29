package com.xdt.dataset_server.Dao.small;

import com.xdt.dataset_server.entity.small.Animal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnimalDao {

    //查找所有的种类
    List<Animal> selectAllAnimal();

    //添加种类
    boolean insertAnimal(Animal animal);

    //删除种类
    boolean delAnimal(String uuid);

    //更新种类
    boolean updateAnimal(Animal animal);

    //查找种类个数
    Integer countAnimal();

    //查找种类-uuid
    Animal selectAnimalByUuid(String uuid);

    //查找种类-nameCn
    List<Animal> selectAnimalByNameCn(String nameCn);

    //查找种类-nameEn
    List<Animal> selectAnimalByNameEn(String nameEn);

}
