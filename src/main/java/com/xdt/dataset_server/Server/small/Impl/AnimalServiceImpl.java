package com.xdt.dataset_server.Server.small.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdt.dataset_server.Dao.small.AnimalDao;
import com.xdt.dataset_server.Server.small.AnimalService;
import com.xdt.dataset_server.entity.small.Animal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalDao animalDao;

    public AnimalServiceImpl(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    @Override
    public List<Animal> selectAllAnimal() {
        return this.animalDao.selectAllAnimal();
    }

    @Override
    public Page<Animal> selectAllAnimalPagination(int currentPage, int pageSize) {
        Page<Animal> animals = PageHelper.startPage(currentPage, pageSize).doSelectPage(() -> this.animalDao.selectAllAnimal());
        return animals;
    }

    @Override
    public boolean insertAnimal(Animal animal) {
        return this.animalDao.insertAnimal(animal);
    }

    @Override
    public boolean delAnimal(String uuid) {
        return this.animalDao.delAnimal(uuid);
    }

    @Override
    public boolean updateAnimal(Animal animal) {
        return this.animalDao.updateAnimal(animal);
    }

    @Override
    public Integer countAnimal() {
        Integer num = this.animalDao.countAnimal();

        return num;
    }

    @Override
    public Animal selectAnimalByUuid(String uuid) {
        return this.animalDao.selectAnimalByUuid(uuid);
    }

    @Override
    public List<Animal> selectAnimalByNameCn(String nameCn) {
        return this.animalDao.selectAnimalByNameCn(nameCn);
    }

    @Override
    public List<Animal> selectAnimalByNameEn(String nameEn) {
        return this.animalDao.selectAnimalByNameEn(nameEn);
    }
}
