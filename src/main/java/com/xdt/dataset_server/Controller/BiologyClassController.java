package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.BiologyClassServerImpl;
import com.xdt.dataset_server.entity.BiologyClass;
import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author XDT
 * @ClassName BiologyClassController
 * @Description: TODO
 * @Date 2023/2/27 20:18
 **/
@RestController
@RequestMapping("/biologyClass")
@Slf4j
public class BiologyClassController {

    @Autowired
    private BiologyClassServerImpl biologyClassServer;

    @PostMapping("/insertBiologyClass")
    public Result insertBiologyClass(@RequestBody BiologyClass biologyClass, HttpServletRequest request){
        if(biologyClass.getNameCn() == null || biologyClass.getNameLatin() == null ){
            return Result.error("300", "参数格式错误");
        }
        System.out.println("biologyClass is " +biologyClass);
        biologyClass.setUuid(IdUtil.simpleUUID());
        biologyClass.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        biologyClass.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        biologyClass.setUserUuid(request.getAttribute("userUuid").toString());
        System.out.println(biologyClass);
        if(biologyClassServer.insertBioloyClass(biologyClass)){
            log.info("添加成功");
            return Result.success();
        }else {
            return Result.error("302", null);
        }
    }
}
