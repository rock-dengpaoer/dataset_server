package com.xdt.dataset_server.Controller;

import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XDT
 * @ClassName CheckController
 * @Description: TODO
 * @Date 2023/2/28 21:33
 **/
@Slf4j
@RestController
@RequestMapping("/check")
public class CheckController {

    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        try {
            String userName = request.getAttribute("userName").toString();
            return Result.success(userName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("123", null);
        }
    }
}
