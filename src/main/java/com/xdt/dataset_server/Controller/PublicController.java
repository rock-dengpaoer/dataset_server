package com.xdt.dataset_server.Controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.xdt.dataset_server.Server.Impl.*;
import com.xdt.dataset_server.entity.*;
import com.xdt.dataset_server.utils.Result;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final BiologyClassServerImpl biologyClassServer;

    private final BiologyOrderServiceImpl biologyOrderService;

    private final BiologyFamilyServiceImpl biologyFamilyService;

    private final BiologySpeciesServiceImpl biologySpeciesService;

    private final BiologyServiceImpl biologyService;

    public PublicController(BiologyClassServerImpl biologyClassServer, BiologyOrderServiceImpl biologyOrderService, BiologyFamilyServiceImpl biologyFamilyService, BiologySpeciesServiceImpl biologySpeciesService, BiologyServiceImpl biologyService) {
        this.biologyClassServer = biologyClassServer;
        this.biologyOrderService = biologyOrderService;
        this.biologyFamilyService = biologyFamilyService;
        this.biologySpeciesService = biologySpeciesService;
        this.biologyService = biologyService;
    }

    /*查询所有纲*/
    @CachePut("selectAllBioloyClass")
    @GetMapping("/selectAllBioloyClass")
    public Result selectAllBioloyClass(){
        System.out.println("get into selectAllBioloyClass");
        return Result.success(biologyClassServer.selectAllBioloyClass());
    }

    /*分页查询所有纲*/
    @CachePut("selectAllBioloyClassPagination")
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
    @CachePut("selectBiologyOrderByClassUuid")
    @GetMapping("/selectBiologyOrderByClassUuid")
    public Result selectBiologyOrderByClassUuid(@RequestParam("classUuid") String classUuid){
        List<BiologyOrder> biologyOrders = this.biologyOrderService.selectBiologyOrderByClassUuid(classUuid);
        return Result.success(biologyOrders);
    }

    /*分页查询所有目*/
    @CachePut("selectBiologyOrderByClassUuidPagination")
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

    @CachePut("selectAllBiologyFamilyByOrderUuid")
    @GetMapping("/selectAllBiologyFamilyByOrderUuid")
    public Result selectAllBiologyFamilyByOrderUuid(@RequestParam("orderUuid") String orderUuid){
        List<BiologyFamily> biologyFamilies = this.biologyFamilyService.selectAllBiologyFamilyByOrderUuid(orderUuid);
        return Result.success(biologyFamilies);
    }

    /*分页查询所有科*/
    @CachePut("selectAllBiologyFamilyByOrderUuidPagination")
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
    @CachePut("selectAllBiologySpeciesByFamilyUuid")
    @GetMapping("/selectAllBiologySpeciesByFamilyUuid")
    public Result selectAllBiologySpeciesByFamilyUuid(@RequestParam("familyUuid") String familyUuid){
        List<BiologySpecies> biologySpecies = this.biologySpeciesService.selectAllBiologySpeciesByFamilyUuid(familyUuid);
        return Result.success(biologySpecies);
    }

    /*分页查询所有种*/
    @CachePut("selectAllBiologySpeciesByFamilyUuidPagination")
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
    @CachePut("countAllBiologySpecies")
    @GetMapping("/countAllBiologySpecies")
    public Result countAllBiologySpecies(){
        System.out.println("get into selectAllBioloySpecies");
        return Result.success(biologySpeciesService.CountAllBiologySpecies());
    }

    @CachePut("getAllNameBySpeciesUuid")
    @GetMapping("/getAllNameBySpeciesUuid")
    public Result getAllNameBySpeciesUuid(@RequestParam String speciesUuid){
        BiologyAllName allName = biologyService.getAllNameBySpeciesUuid(speciesUuid);
        //String name = allName.getClassLatinName() + "_" + allName.getOrderLatinName() + "_" + allName.getFamilyLatinName() + "_" + allName.getSpeciesLatinName();
        return Result.success(allName.toString());
    }
}
