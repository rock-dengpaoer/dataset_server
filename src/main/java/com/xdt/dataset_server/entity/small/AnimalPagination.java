package com.xdt.dataset_server.entity.small;

import lombok.Data;

import java.util.List;

@Data
public class AnimalPagination {
    /*当前页*/
    private int currentPageNum;
    /*总页数*/
    private int totalPage;
    /*每页记录数量*/
    private int pageSize;
    /*总记录数*/
    private long totalCount;
    /*返回的结果*/
    private List<Animal> animalList;
}
