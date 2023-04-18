package com.xdt.dataset_server.Controller.small;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xdt.dataset_server.Server.small.Impl.AnimalServiceImpl;
import com.xdt.dataset_server.entity.small.Animal;
import com.xdt.dataset_server.utils.MinioUtil;
import com.xdt.dataset_server.utils.Msg;
import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/small/animal/")
public class AnimalController {
    private final AnimalServiceImpl animalService;
    private final MinioUtil minioUtil;

    public AnimalController(AnimalServiceImpl animalService, MinioUtil minioUtil) {
        this.animalService = animalService;
        this.minioUtil = minioUtil;
    }

    //查询个数
    @GetMapping("/allNum")
    public Result allNum(){
        Integer num = this.animalService.countAnimal();
        return Result.success(num);
    }

    //查找所有种类
    @GetMapping("/selectAll")
    public Result selectAllAnimal(){
        List<Animal> animalList = this.animalService.selectAllAnimal();
        return Result.success(animalList);
    }

    //新增种类
    @PostMapping("/insert")
    public Result insert(@RequestBody Animal animal, HttpServletRequest request){
        if(animal.getNameEn() == null ){
            return Result.error("300", "种类名不能未空");
        }

        if(!animal.getNameEn().matches("^[0-9a-zA-Z]+$")){
            return Result.error("300", "种类名不符合命名规范");
        }

        //设置uuid
        animal.setUuid(IdUtil.simpleUUID());
        animal.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        animal.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        animal.setUserUuid(request.getAttribute("userUuid").toString());


        //TODO： 修改未先建桶，再添加到数据库

        if(this.animalService.insertAnimal(animal)){
            //创建存储缩略图的桶
            Msg msg = minioUtil.makeBucket(animal.getUuid());

            //创建储存原图的桶
            Msg msg1 = minioUtil.makeBucket(animal.getNameEn());
            if(msg.isFlag()){
                if(msg1.isFlag()){
                    return Result.success("添加成功");
                }else{
                    log.error(msg1.getMsg1());
                    return Result.error("500", msg1.getMsg1());
                }
            }else {
                log.error(msg.getMsg1());
                return Result.error("500", msg.getMsg1());
            }
        }
        return Result.error("500", "添加失败");
    }

    @DeleteMapping("/del")
    public Result del(@RequestParam String uuid){
        if(this.animalService.delAnimal(uuid)){

            //TODO：这里删除minio存储的文件

            return Result.success("删除成功");
        }
        return Result.error("500", "删除失败");
    }

    @PutMapping("/update")
    public Result update(@RequestBody Animal animal){

        //获取到原先的minio-bucket的名字
        Animal oldAnimal = this.animalService.selectAnimalByUuid(animal.getUuid());
        log.info(oldAnimal.toString());

        animal.setUpdateTime(DateUtil.date(System.currentTimeMillis()));

        if(!animal.getNameEn().equals(oldAnimal.getNameEn())){
            return Result.error("400", "英文名不允许修改");
        }

        if(this.animalService.updateAnimal(animal)){
            return Result.success("更新成功");
        }
        return Result.error("500", "更新失败");
    }

    @GetMapping("/selectAnimalByUuid")
    public Result selectAnimalByUuid(@RequestParam String uuid){
        Animal animal = this.animalService.selectAnimalByUuid(uuid);
        if(ObjectUtil.isNotEmpty(animal)){
            return Result.success(animal);
        }
        return Result.error("300", "没有找到");
    }

    @GetMapping("/selectAnimalByNameCn")
    public Result selectAnimalByNameCn(@RequestParam String nameCn){
        List<Animal> animalList = this.animalService.selectAnimalByNameCn(nameCn);
        if(animalList.size() == 0){
            return Result.error("300", "没有找到");
        }
        return Result.success(animalList);
    }

    @GetMapping("/selectAnimalByNameEn")
    public Result selectAnimalByNameEn(@RequestParam String nameEn){
        List<Animal> animalList = this.animalService.selectAnimalByNameEn(nameEn);
        if(animalList.size() == 0){
            return Result.error("300", "没有找到");
        }
        return Result.success(animalList);
    }
}
