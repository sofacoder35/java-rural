package com.sofa.controller;

import com.sofa.entity.News;
import com.sofa.service.NewsService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@Api(tags = "运动咨询管理")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @ApiOperation(value = "分页查找")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo){
        return newsService.findPage(queryInfo);
    }

    @ApiOperation(value = "查找全部")
    @PostMapping("/findAll")
    public Result findAll(){
       return newsService.findAll();
    }



    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return newsService.delete(id);
    }


    @ApiOperation(value = "添加")
    @PostMapping("/insert")
    public Result insert(@RequestBody News news){
        return newsService.insert(news);
    }

    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result update(@RequestBody News news){
        return newsService.update(news);
    }

}
