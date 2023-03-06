package com.xdt.dataset_server.Controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.xdt.dataset_server.Server.Impl.MinioObjectServiceImpl;
import com.xdt.dataset_server.entity.MinioObject;
import com.xdt.dataset_server.entity.MinioObjectPagination;
import com.xdt.dataset_server.entity.ObjectInfo;
import com.xdt.dataset_server.utils.MinioUtil;
import com.xdt.dataset_server.utils.Msg;
import com.xdt.dataset_server.utils.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XDT
 * @ClassName MInioController
 * @Description: TODO
 * @Date 2023/2/28 15:24
 **/
@Slf4j
@RestController
@RequestMapping("/minio/")
public class MInioController {

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private MinioObjectServiceImpl minioObjectService;

    @PostMapping(value = {"insertMinioObject"}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result insertMinioObject(String speciesUuid,
                                    @RequestParam("file") MultipartFile file,
                                    HttpServletRequest request){
        Msg msg;
        try {
            msg = minioUtil.putObject(file, speciesUuid);
            ObjectInfo objectInfo = minioUtil.statObject(speciesUuid, msg.getMsg2());

            String userUuid = request.getAttribute("userUuid").toString();
            System.out.println(userUuid);

            MinioObject minioObject = new MinioObject();
            minioObject.setUuid(IdUtil.simpleUUID());
            minioObject.setObjectName(objectInfo.getName());
            minioObject.setType(objectInfo.getType());
            minioObject.setSize(objectInfo.getSize());
            minioObject.setUserUuid(userUuid);
            minioObject.setUploadTime(new Date());
            minioObject.setBucketName(speciesUuid);

            if(this.minioObjectService.insertMinioObject(minioObject)){
                return Result.success();
            }else {
                return Result.error("123", "失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*按category查询所有img信息--分页*/
    @GetMapping(value = {"/SelectAllImgInfoFromCategoryPagination"})
    public Result SelectAllImgInfoFromCategoryPagination(
            @RequestParam("speciesUuid") String speciesUuid,
            @RequestParam("currentPage") String currentPage,
            @RequestParam("pageSize") String pageSize) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("post into SelectAllImgInfoFromCategoryPagination");

        //if(categoryUuid.equals("undefined")){
        //    BiologySpecies biologySpecies = this.biologySpeciesService.selectBiologySpeciesByNameLatin("undefined");
        //    categoryUuid = biologySpecies.getUuid();
        //}

        log.info("categoryUuid currentPage pageSize" + speciesUuid + " " + currentPage + " " + pageSize);

        int currentPageInt = 0;
        int pageSizeInt = 0;
        try {
            currentPageInt = Integer.parseInt(currentPage);
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return Result.error("123", "currentPage 或 pageSize 不对");
        }

        Page<MinioObject> minioObjectPage = minioObjectService.selectAllObjectByBucketNamePagination(speciesUuid, currentPageInt, pageSizeInt);
        if(ObjectUtil.isNull(minioObjectPage)){
            return Result.notFind("not find any data in "+ speciesUuid);
        }
        MinioObjectPagination minioObjectPagination =new MinioObjectPagination();
        /*每页记录数量*/
        minioObjectPagination.setPageSize(minioObjectPage.getPageSize());
        /*总页数*/
        minioObjectPagination.setTotalCount(minioObjectPage.getTotal());
        /*当前页*/
        minioObjectPagination.setCurrentPageNum(currentPageInt);
        /*总记录数*/
        minioObjectPagination.setTotalPage(minioObjectPage.getPages());

        ///*添加图片url*/
        /*提取出查询到信息*/
        List<MinioObject> minioObjectList = minioObjectPage.getResult();
        for(int i = 0; i < minioObjectList.size(); i++){
            MinioObject minioObject = minioObjectList.get(i);
            log.warn("minioObject info");
            log.warn(String.valueOf(minioObject));
            //获取分享链接
            Msg msg = minioUtil.presignifyGetObject(minioObject.getBucketName(), minioObject.getObjectName(), 200);
            //出错图片链接
            Msg error_msg = minioUtil.presignifyGetObject("picture-notfind", "error.jpg", 20);
            if(msg.isFlag()){
                minioObject.setUrl(msg.getMsg1());
            }else {
                minioObject.setUrl(error_msg.getMsg1());
            }
        }
        minioObjectPagination.setMinioObjectList(minioObjectList);
        return Result.success(minioObjectPagination);
    }

    @GetMapping("/listAllBucket")
    public Result listAllBucket(){
        List<Bucket> buckets = minioUtil.listAllBucket();
        for(int i = 0; i<buckets.size(); i++) {
            Bucket bucket = buckets.get(i);
            System.out.println(bucket.name());
        }
        return Result.success(buckets);
    }

    @GetMapping("/getPresignifyGetObject")
    public Result getPresignifyGetObject(@RequestParam("speciesUuid") String speciesUuid,
                                         @RequestParam("objectName") String objectName){
        Msg msg = minioUtil.presignifyGetObject(speciesUuid, objectName, 200);
        return Result.success(msg.getMsg1());
    }

    @GetMapping("/getAllSpeciesUrl")
    public Result getAllSpeciesUrl(@RequestParam("speciesUuid") String speciesUuid){
        List<MinioObject> minioObjects = minioObjectService.selectAllObjectByBucketName(speciesUuid);
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i< minioObjects.size(); i++){
            MinioObject minioObject = minioObjects.get(i);
            Msg msg = minioUtil.presignifyGetObject(speciesUuid, minioObject.getObjectName(), 200);
            map.put(minioObject.getObjectName(), msg.getMsg1());
        }
        return Result.success(map);
    }
}
