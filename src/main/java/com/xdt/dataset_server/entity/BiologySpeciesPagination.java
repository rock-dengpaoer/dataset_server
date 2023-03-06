package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyOrder
 * @Description: TODO
 * @Date 2023/2/28 10:23
 **/
@Data
public class BiologySpeciesPagination {
    /*当前页*/
    private int currentPageNum;
    /*总页数*/
    private int totalPage;
    /*每页记录数量*/
    private int pageSize;
    /*总记录数*/
    private long totalCount;
    /*返回的结果*/
    private List<BiologySpecies> biologySpeciesList;
}
