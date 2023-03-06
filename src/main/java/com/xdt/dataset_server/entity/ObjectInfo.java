package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author XDT
 * @ClassName ObjectInfo
 * @Description: TODO
 * @Date 2023/2/28 11:12
 **/
@Data
public class ObjectInfo {
    //    id
    private Integer id;
    //    对象名称
    private String name;
    //    缩略图对应的视频文件名称
    private String videoName;
    //    创建时间
    private Date createTime;
    //    更新时间
    private Date updateTime;
    //    状态，0禁用，1开启，9删除
    private Integer status;
    //    种类，所属bucket
    private String category;
    //    对象大小
    private double size;
    //    转换过的对象大小
    private String StringSize;
    //    显示的图片 blob格式
    private String previewImg;
    //    下载链接
    private String downloadUrl;
    //    预览链接
    private String previewUrl;
    //    删除链接
    private String deleteUrl;
    //    编辑链接
    private String editUrl;
    //    对象类别， img图片，video视频
    private String type;
    //    中文对象类别
    private String typeCn;
    //    所属者
    private String user;
}
