package com.sofa.controller;


import com.sofa.utils.BaiduBceUtils;
import com.sofa.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/baidu")
@Slf4j
public class BaiduController {

    @Autowired
    private BaiduBceUtils baiduBceUtils;

    @PostMapping("/baiduClassify")
    public Result baiduClassify(String image){
        log.info("base64 controller--{}",image);
        return Result.success("识别成功",baiduBceUtils.baiduClassify(image));
    }
}
