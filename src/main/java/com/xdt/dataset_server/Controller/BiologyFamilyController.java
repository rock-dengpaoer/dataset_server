package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.BiologyFamilyServiceImpl;
import com.xdt.dataset_server.entity.BiologyFamily;
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
 * @ClassName BiologyFamilyController
 * @Description: TODO
 * @Date 2023/3/4 16:29
 **/
@RestController
@RequestMapping("biologyFamily")
@Slf4j
public class BiologyFamilyController {

    @Autowired
    private BiologyFamilyServiceImpl biologyFamilyService;

    @PostMapping("insertBiologyFamily")
    public Result insertBiologyFamily(@RequestBody BiologyFamily biologyFamily, HttpServletRequest request){
        if(biologyFamily.getNameCn() == null || biologyFamily.getNameLatin() == null || biologyFamily.getOrderUuid() == null){
            return Result.error("300", "参数格式错误");
        }

        if(!biologyFamily.getNameLatin().matches("^[0-9a-zA-Z]+$")){
            return Result.error("300", "拉丁名不符合命名规范");
        }

        /*将首字母大写*/
        String NameLatin = Gadget.isUppercase(biologyFamily.getNameLatin());
        biologyFamily.setNameLatin(NameLatin);


        System.out.println("biologyFamily is " +biologyFamily);
        biologyFamily.setUuid(IdUtil.simpleUUID());
        biologyFamily.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        biologyFamily.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        biologyFamily.setUserUuid(request.getAttribute("userUuid").toString());
        System.out.println(biologyFamily);
        if(biologyFamilyService.insertBiologyFamily(biologyFamily)){
            log.info("添加成功");
            return Result.success();
        }else {
            return Result.error("302", null);
        }
    }
}
