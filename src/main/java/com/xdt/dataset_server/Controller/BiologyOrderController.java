package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.BiologyOrderServiceImpl;
import com.xdt.dataset_server.entity.BiologyOrder;
import com.xdt.dataset_server.utils.Gadget;
import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XDT
 * @ClassName BiologyOrderController
 * @Description: TODO
 * @Date 2023/3/4 16:28
 **/
@RestController
@RequestMapping("biologyOrder")
@Slf4j
public class BiologyOrderController {

    @Autowired
    private BiologyOrderServiceImpl biologyOrderService;

    @PostMapping("insertBiologyOrder")
    public Result insertBiologyOrder(@RequestBody BiologyOrder biologyOrder, HttpServletRequest request){
        if(biologyOrder.getNameCn() == null || biologyOrder.getNameLatin() == null || biologyOrder.getClassUuid() == null){
            return Result.error("300", "参数格式错误");
        }

        if(!biologyOrder.getNameLatin().matches("^[0-9a-zA-Z]+$")){
            return Result.error("300", "拉丁名不符合命名规范");
        }

        /*将首字母大写*/
        String NameLatin = Gadget.isUppercase(biologyOrder.getNameLatin());
        biologyOrder.setNameLatin(NameLatin);

        System.out.println("BiologyOrder is " +biologyOrder);
        biologyOrder.setUuid(IdUtil.simpleUUID());
        biologyOrder.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        biologyOrder.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        biologyOrder.setUserUuid(request.getAttribute("userUuid").toString());
        System.out.println(biologyOrder);
        if(biologyOrderService.insertBiologyOrder(biologyOrder)){
            log.info("添加成功");
            return Result.success();
        }else {
            return Result.error("302", null);
        }
    }
}
