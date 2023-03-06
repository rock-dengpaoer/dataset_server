package com.xdt.dataset_server.utils;

import cn.hutool.core.date.DateUtil;
import com.xdt.dataset_server.entity.ObjectInfo;
import io.minio.*;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author XDT
 * @ClassName MinioUtil
 * @Description: TODO
 * @Date 2023/2/28 11:09
 **/
@Slf4j
@Component
public class MinioUtil {
    private final MinioClient minioClient;

    public MinioUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    //    创建bucket
    public Msg makeBucket(String bucketName){
        try {
            // 如存储桶不存在，创建之。
            boolean found = bucketExists(bucketName);
            if (found) {
                return Msg.msgReslut(false, bucketName + " 已经存在");
            } else {
                // 创建名为'my-bucketname'的存储桶。
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                return Msg.msgReslut(true, bucketName + " 创建成功");
            }
        } catch (MinioException e) {
            return Msg.msgReslut(false, String.valueOf(e));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    //    检测存储桶是否存在
    public boolean bucketExists(String bucketName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                log.info(bucketName + " exits");
                return true;
            } else {
                log.info(bucketName + " not exits");
                return false;
            }
        } catch (MinioException e) {
            log.info("Error occurred: " + e);
        } catch (IOException | IllegalArgumentException e) {
            log.info("bucket 命名有误");
            return false;
//            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //    列出所有bucket
    public List<Bucket> listAllBucket() {
        try {
            List<Bucket> bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                log.info(bucket.creationDate() + ", " + bucket.name());
            }
            return bucketList;
        } catch (MinioException e) {
            log.info("Error occurred: " + e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //    列出某个存储桶中的所有对象。
    public Iterable<Result<Item>> listAllObjectsInBucket(String bucketName) {
        // 检查'mybucket'是否存在。
        boolean found = bucketExists(bucketName);
        if (found) {
            // 列出'my-bucketname'里的对象
            Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            return myObjects;
        } else {
            System.out.println("mybucket does not exist");
            return null;
        }
    }

    //    读取图片
    public InputStream getObject(String bucketName, String objectName) {
        try {
//            调用statObject()来判断对象是否存在
//            如果不存在，statObject()抛出异常
//            否则代表对象存在
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

//            获取myobject的输入流
            InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());

            return stream;
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    //    获取对象的元数据。
    public ObjectInfo statObject(String bucketName, String objectName) {
        try {
            StatObjectResponse objectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            ObjectInfo objectInfo = new ObjectInfo();
            objectInfo.setCategory(bucketName);
            objectInfo.setName(objectName);
            objectInfo.setSize(objectResponse.size());
            Date date = DateUtil.parse(String.valueOf(objectResponse.lastModified()));
            objectInfo.setUpdateTime(date);
            return objectInfo;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    //    上传对象
    public Msg putObject(MultipartFile files, String bucketName){
        log.info("into minio putObject");
        try {
//            判断文件是否为空
            if(files == null || 0 == files.getSize()){
                log.error("文件为空");
                return Msg.msgReslut(false, "文件为空");
            }
//            判断bucket是否存在
            if(!bucketExists(bucketName)){
                log.error(bucketName + " not exit");
                return Msg.msgReslut(false, bucketName + " not exit");
            }

            //文件名
            String originalFilename = files.getOriginalFilename();

            assert originalFilename != null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            //新的文件名 = 存储桶文件名_时间戳.后缀名
            String fileName = bucketName + "_" +
                    System.currentTimeMillis() + "_" + format.format(new Date()) + "_" + new Random().nextInt(1000) +
                    originalFilename.substring(originalFilename.lastIndexOf("."));
            log.info("开始上传");

            // 创建对象, partsize 不设置
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(files.getInputStream(), files.getSize(), -1)
                    .contentType(files.getContentType())
                    .build());
            log.info(fileName + "上传成功");
            return Msg.msgReslut2Msg(true, fileName +  " 上传成功", fileName);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (FileSizeLimitExceededException e){
            return Msg.msgReslut(false, "文件太大");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return Msg.msgReslut(false, "上传出现异常");
    }

    //    上传对象使用流
    public Msg putObjectByInputStream(InputStream stream, String bucketName, String fileName, long fileSize, String contentType){
        log.info("into minio putObject");
        try {
//            判断bucket是否存在
            if(!bucketExists(bucketName)){
                log.error(bucketName + " not exit");
                return Msg.msgReslut(false, bucketName + " not exit");
            }
            log.info("开始上传");

            // 创建对象, partsize 不设置
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(stream, fileSize, -1)
                    .contentType(contentType)
                    .build());
            log.info(fileName + "上传成功");
            return Msg.msgReslut2Msg(true, fileName +  " 上传成功", fileName);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (FileSizeLimitExceededException e){
            return Msg.msgReslut(false, "文件太大");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return Msg.msgReslut(false, "上传出现异常");
    }

    /*获取分享链接*/
    public Msg presignifyGetObject(String bucketName, String objectName, Integer expires){
        Msg msg = new Msg();
        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).expiry(expires).method(Method.GET).build());
            msg.setFlag(true);
            msg.setMsg1(url);
        } catch (ErrorResponseException| InsufficientDataException |InternalException |InvalidKeyException |InvalidResponseException| IOException| NoSuchAlgorithmException| XmlParserException| ServerException e){
            log.error(e.getMessage());
            msg.setFlag(false);
            msg.setMsg1(e.getMessage());
        }
        return msg;
    }
}
