package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.BiologySpeciesServiceImpl;
import com.xdt.dataset_server.entity.BiologySpecies;
import com.xdt.dataset_server.utils.MinioUtil;
import com.xdt.dataset_server.utils.Msg;
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
 * @ClassName BiologySpeciesController
 * @Description: TODO
 * @Date 2023/3/4 16:29
 **/
@RestController
@RequestMapping("biologySpecies")
@Slf4j
public class BiologySpeciesController {

    @Autowired
    private BiologySpeciesServiceImpl biologySpeciesService;

    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("insertBiologySpecies")
    public Result insertBiologySpecies(@RequestBody BiologySpecies biologySpecies, HttpServletRequest request){
        if(biologySpecies.getNameCn() == null || biologySpecies.getNameLatin() == null || biologySpecies.getFamilyUuid() == null){
            return Result.error("300", "参数格式错误");
        }
        System.out.println("biologySpecies is " +biologySpecies);
        biologySpecies.setUuid(IdUtil.simpleUUID());
        biologySpecies.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        biologySpecies.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        biologySpecies.setUserUuid(request.getAttribute("userUuid").toString());
        System.out.println(biologySpecies);
        if(biologySpeciesService.insertBiologySpecies(biologySpecies)){
            log.info("添加成功");
            Msg msg = minioUtil.makeBucket(biologySpecies.getUuid());
            if(msg.isFlag()){
                return Result.success();
            }else {
                log.error(msg.getMsg1());
                return Result.error("500", "创建存储池失败");
            }

        }else {
            return Result.error("302", null);
        }
    }
}
