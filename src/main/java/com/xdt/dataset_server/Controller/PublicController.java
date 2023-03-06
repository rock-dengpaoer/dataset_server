package com.xdt.dataset_server.Controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.xdt.dataset_server.Server.Impl.*;
import com.xdt.dataset_server.entity.*;
import com.xdt.dataset_server.utils.MinioUtil;
import com.xdt.dataset_server.utils.Msg;
import com.xdt.dataset_server.utils.Result;
import com.github.pagehelper.Page;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XDT
 * @ClassName FreeController
 * @Description: TODO
 * @Date 2023/2/27 21:24
 **/
@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
    @Autowired
    private BiologyClassServerImpl biologyClassServer;

    @Autowired
    private BiologyOrderServiceImpl biologyOrderService;

    @Autowired
    private BiologyFamilyServiceImpl biologyFamilyService;

    @Autowired
    private BiologySpeciesServiceImpl biologySpeciesService;

    @Autowired
    private MinioObjectServiceImpl minioObjectService;

    /*查询所有纲*/
    @GetMapping("/selectAllBioloyClass")
    public Result selectAllBioloyClass(){
        System.out.println("get into selectAllBioloyClass");
        return Result.success(biologyClassServer.selectAllBioloyClass());
    }

    /*分页查询所有纲*/
    @GetMapping(value = {"/selectAllBioloyClassPagination"})
    public Result selectAllBiologyClassPagination(@RequestParam("currentPage") String currentPage,
                                                  @RequestParam("pageSize") String pageSize) {

        int currentPageInt = 0;
        int pageSizeInt = 0;
        try {
            currentPageInt = Integer.parseInt(currentPage);
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return Result.error("123", "currentPage 或 pageSize 不对");
        }

        log.info("post into selectAllBiologyClassPagination");
        Page<BiologyClass> biologyClasses = this.biologyClassServer.selectAllBiologyClassPagination(currentPageInt, pageSizeInt);
        if (ObjectUtil.isNull(biologyClasses)) {
            return Result.notFind("还没有纲,快去创建一个吧!");
        }
        BiologyClassPagination biologyClassPagination = new BiologyClassPagination();
        /*每页记录数量*/
        biologyClassPagination.setPageSize(biologyClasses.getPageSize());
        /*总页数*/
        biologyClassPagination.setTotalCount(biologyClasses.getTotal());
        /*当前页*/
        biologyClassPagination.setCurrentPageNum(currentPageInt);
        /*总记录数*/
        biologyClassPagination.setTotalPage(biologyClasses.getPages());
        biologyClassPagination.setBiologyClassList(biologyClasses.getResult());
            return Result.success(JSONUtil.toJsonStr(biologyClassPagination));
    }

    /*查询所有目*/
    @GetMapping("/selectBiologyOrderByClassUuid")
    public Result selectBiologyOrderByClassUuid(@RequestParam("classUuid") String classUuid){
        List<BiologyOrder> biologyOrders = this.biologyOrderService.selectBiologyOrderByClassUuid(classUuid);
        return Result.success(biologyOrders);
    }

    /*分页查询所有目*/
    @GetMapping(value = {"/selectBiologyOrderByClassUuidPagination"})
    public Result selectBiologyOrderByClassUuidPagination(@RequestParam("classUuid") String classUuid,
                                                  @RequestParam("currentPage") String currentPage,
                                                  @RequestParam("pageSize") String pageSize) {

        int currentPageInt = 0;
        int pageSizeInt = 0;
        try {
            currentPageInt = Integer.parseInt(currentPage);
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return Result.error("123", "currentPage 或 pageSize 不对");
        }

        log.info("post into selectBiologyOrderByClassUuidPagination");
        Page<BiologyOrder> biologyOrders = this.biologyOrderService.selectBiologyOrderByClassUuidPagination(classUuid, currentPageInt, pageSizeInt);
        if (biologyOrders.size() == 0) {
            return Result.notFind("还没有目,快去创建一个吧!");
        }
        BiologyOrderPagination biologyOrderPagination = new BiologyOrderPagination();
        /*每页记录数量*/
        biologyOrderPagination.setPageSize(biologyOrders.getPageSize());
        /*总页数*/
        biologyOrderPagination.setTotalCount(biologyOrders.getTotal());
        /*当前页*/
        biologyOrderPagination.setCurrentPageNum(currentPageInt);
        /*总记录数*/
        biologyOrderPagination.setTotalPage(biologyOrders.getPages());
        biologyOrderPagination.setBiologyOrderList(biologyOrders.getResult());
        return Result.success(JSONUtil.toJsonStr(biologyOrderPagination));
    }

    @GetMapping("/selectAllBiologyFamilyByOrderUuid")
    public Result selectAllBiologyFamilyByOrderUuid(@RequestParam("orderUuid") String orderUuid){
        List<BiologyFamily> biologyFamilies = this.biologyFamilyService.selectAllBiologyFamilyByOrderUuid(orderUuid);
        return Result.success(biologyFamilies);
    }

    /*分页查询所有科*/
    @GetMapping(value = {"/selectAllBiologyFamilyByOrderUuidPagination"})
    public Result selectAllBiologyFamilyByOrderUuidPagination(@RequestParam("orderUuid") String orderUuid,
                                                          @RequestParam("currentPage") String currentPage,
                                                          @RequestParam("pageSize") String pageSize) {

        int currentPageInt = 0;
        int pageSizeInt = 0;
        try {
            currentPageInt = Integer.parseInt(currentPage);
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return Result.error("123", "currentPage 或 pageSize 不对");
        }

        log.info("post into selectAllBiologyFamilyByOrderUuidPagination");
        Page<BiologyFamily> biologyFamilies = this.biologyFamilyService.selectAllBiologyFamilyByOrderUuidPagination(orderUuid, currentPageInt, pageSizeInt);
        if (biologyFamilies.size() == 0) {
            return Result.notFind("还没有目,快去创建一个吧!");
        }
        BiologyFamilyPagination biologyFamilyPagination = new BiologyFamilyPagination();
        /*每页记录数量*/
        biologyFamilyPagination.setPageSize(biologyFamilies.getPageSize());
        /*总页数*/
        biologyFamilyPagination.setTotalCount(biologyFamilies.getTotal());
        /*当前页*/
        biologyFamilyPagination.setCurrentPageNum(currentPageInt);
        /*总记录数*/
        biologyFamilyPagination.setTotalPage(biologyFamilies.getPages());
        biologyFamilyPagination.setBiologyFamilyList(biologyFamilies.getResult());
        return Result.success(JSONUtil.toJsonStr(biologyFamilyPagination));
    }

    /*查询所有的种， 通过familyUuid*/
    @GetMapping("/selectAllBiologySpeciesByFamilyUuid")
    public Result selectAllBiologySpeciesByFamilyUuid(@RequestParam("familyUuid") String familyUuid){
        List<BiologySpecies> biologySpecies = this.biologySpeciesService.selectAllBiologySpeciesByFamilyUuid(familyUuid);
        return Result.success(biologySpecies);
    }

    /*分页查询所有种*/
    @GetMapping(value = {"/selectAllBiologySpeciesByFamilyUuidPagination"})
    public Result selectAllBiologySpeciesByFamilyUuidPagination(@RequestParam("familyUuid") String familyUuid,
                                                              @RequestParam("currentPage") String currentPage,
                                                              @RequestParam("pageSize") String pageSize) {

        int currentPageInt = 0;
        int pageSizeInt = 0;
        try {
            currentPageInt = Integer.parseInt(currentPage);
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return Result.error("123", "currentPage 或 pageSize 不对");
        }

        log.info("post into selectAllBiologySpeciesByFamilyUuidPagination");
        Page<BiologySpecies> biologySpecies = this.biologySpeciesService.selectAllBiologySpeciesByFamilyUuidPagination(familyUuid, currentPageInt, pageSizeInt);
        if (biologySpecies.size() == 0) {
            return Result.notFind("还没有目,快去创建一个吧!");
        }
        BiologySpeciesPagination biologySpeciesPagination = new BiologySpeciesPagination();
        /*每页记录数量*/
        biologySpeciesPagination.setPageSize(biologySpecies.getPageSize());
        /*总页数*/
        biologySpeciesPagination.setTotalCount(biologySpecies.getTotal());
        /*当前页*/
        biologySpeciesPagination.setCurrentPageNum(currentPageInt);
        /*总记录数*/
        biologySpeciesPagination.setTotalPage(biologySpecies.getPages());
        biologySpeciesPagination.setBiologySpeciesList(biologySpecies.getResult());
        return Result.success(JSONUtil.toJsonStr(biologySpeciesPagination));
    }

    /*查询所有纲*/
    @GetMapping("/countAllBiologySpecies")
    public Result countAllBiologySpecies(){
        System.out.println("get into selectAllBioloySpecies");
        return Result.success(biologySpeciesService.CountAllBiologySpecies());
    }
}
