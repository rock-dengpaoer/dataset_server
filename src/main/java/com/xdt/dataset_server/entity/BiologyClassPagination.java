package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyClassPagination
 * @Description: TODO
 * @Date 2023/3/1 8:53
 **/
@Data
public class BiologyClassPagination{
    /*当前页*/
    private int currentPageNum;
    /*总页数*/
    private int totalPage;
    /*每页记录数量*/
    private int pageSize;
    /*总记录数*/
    private long totalCount;
    /*返回的结果*/
    private List<BiologyClass> biologyClassList;
}
