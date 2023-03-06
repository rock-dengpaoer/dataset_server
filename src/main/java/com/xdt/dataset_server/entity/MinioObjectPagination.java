package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author XDT
 * @ClassName Object
 * @Description: TODO
 * @Date 2023/2/28 11:29
 **/
@Data
public class MinioObjectPagination {
    /*当前页*/
    private int currentPageNum;
    /*总页数*/
    private int totalPage;
    /*每页记录数量*/
    private int pageSize;
    /*总记录数*/
    private long totalCount;
    /*返回的结果*/
    private List<MinioObject> minioObjectList;
}
