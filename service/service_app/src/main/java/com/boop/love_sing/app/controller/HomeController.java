package com.boop.love_sing.app.controller;


import com.boop.love_sing.common.config.QinglongUtil;
import com.boop.love_sing.common.result.Result;
import com.qingstor.sdk.exception.QSException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/api/app/")
public class HomeController {

    @PostMapping("/hello")
    public Result<String> hello(MultipartFile file){
        try {
            System.out.println(QinglongUtil.putObject("test",file.getInputStream()));
        } catch (QSException | IOException e) {
            e.printStackTrace();
        }
        return Result.ok("Hello!!");
    }
}
