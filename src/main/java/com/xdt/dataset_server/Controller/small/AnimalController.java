package com.xdt.dataset_server.Controller.small;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.xdt.dataset_server.Server.small.Impl.AnimalObjectServiceImpl;
import com.xdt.dataset_server.Server.small.Impl.AnimalServiceImpl;
import com.xdt.dataset_server.entity.MinioObject;
import com.xdt.dataset_server.entity.MinioObjectPagination;
import com.xdt.dataset_server.entity.ObjectInfo;
import com.xdt.dataset_server.entity.small.Animal;
import com.xdt.dataset_server.entity.small.AnimalObjectInfo;
import com.xdt.dataset_server.utils.MinioUtil;
import com.xdt.dataset_server.utils.Msg;
import com.xdt.dataset_server.utils.Result;
import com.xdt.dataset_server.utils.ThumbnailUtil;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/small/animal/")
public class AnimalController {
    private final AnimalServiceImpl animalService;
    private final MinioUtil minioUtil;
    private final ThumbnailUtil thumbnailUtil;
    private final AnimalObjectServiceImpl animalObjectService;

    public AnimalController(AnimalServiceImpl animalService, MinioUtil minioUtil, ThumbnailUtil thumbnailUtil, AnimalObjectServiceImpl animalObjectService) {
        this.animalService = animalService;
        this.minioUtil = minioUtil;
        this.thumbnailUtil = thumbnailUtil;
        this.animalObjectService = animalObjectService;
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

    //上传文件
    @PostMapping(value = {"insertAnimalObject"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result insertAnimalObject(String uuid,
                                    @RequestParam("file") MultipartFile file,
                                    HttpServletRequest request){

        Animal animal = this.animalService.selectAnimalByUuid(uuid);

        Msg msg;
        try {
            msg = minioUtil.putObject(file, animal.getNameEn());


            //制作缩略图
            InputStream object = minioUtil.getObject(animal.getNameEn(), msg.getMsg2());
            String newName = thumbnailUtil.getThumbnail(object, msg.getMsg2());
            File newFile = new File(newName);
            long fileSize = newFile.length();
            InputStream input = Files.newInputStream(Paths.get(newName));
            Path path = new File(newName).toPath();
            String mimeType = Files.probeContentType(path);
            Msg msg1 = minioUtil.putObjectByInputStream(input, uuid, newName, fileSize, mimeType);
            input.close();
            if(newFile.delete()){
                log.warn(newFile.getName() + " 文件已被删除！");
            }else{
                log.error(newFile.getName() + " 文件删除失败！");
            }


            String userUuid = request.getAttribute("userUuid").toString();

            AnimalObjectInfo animalObjectInfo = new AnimalObjectInfo();
            animalObjectInfo.setUuid(IdUtil.simpleUUID());
            animalObjectInfo.setBucketName(uuid);
            animalObjectInfo.setName(newFile.getName());
            animalObjectInfo.setCreateTime(new Date());
            animalObjectInfo.setUserUuid(userUuid);

            AnimalObjectInfo animalObjectInfo1 = new AnimalObjectInfo();
            animalObjectInfo1.setUuid(IdUtil.simpleUUID());
            animalObjectInfo1.setBucketName(animal.getNameEn());
            animalObjectInfo1.setName(msg.getMsg2());
            animalObjectInfo1.setCreateTime(new Date());
            animalObjectInfo1.setUserUuid(userUuid);


            if(this.animalObjectService.insert(animalObjectInfo)){
                this.animalObjectService.insert(animalObjectInfo1);
                return Result.success("上传成功");
            }else {
                return Result.error("300", "失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.error("500", "失败");
        }
    }

    //TODO: 分页
    /*按uuid查询所有img信息, 返回的是缩略图*/
    @GetMapping(value = {"/selectAllImgInfoByUuid"})
    public Result SelectAllImgInfoByUuid(
            @RequestParam("uuid") String uuid,
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {


        List<AnimalObjectInfo> animalObjectInfos = this.animalObjectService.selectByBucketName(uuid);


        ///*添加图片url*/
        /*提取出查询到信息*/
        for(int i = 0; i < animalObjectInfos.size(); i++){
            AnimalObjectInfo animalObjectInfo = animalObjectInfos.get(i);
            log.warn("animalObjectInfo ");
            log.warn(String.valueOf(animalObjectInfo));
            //获取分享链接
            Msg msg = minioUtil.presignifyGetObject(animalObjectInfo.getBucketName(), animalObjectInfo.getName(), 200);
            //出错图片链接
            Msg error_msg = minioUtil.presignifyGetObject("picture-notfind", "error.jpg", 20);
            if(msg.isFlag()){
                animalObjectInfo.setUrl(msg.getMsg1());
            }else {
                animalObjectInfo.setUrl(error_msg.getMsg1());
            }
        }
        return Result.success(animalObjectInfos);
    }
}
