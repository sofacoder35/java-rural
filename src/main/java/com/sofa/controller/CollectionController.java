package com.sofa.controller;

import com.sofa.entity.Collection;
import com.sofa.entity.News;
import com.sofa.service.CollectionService;
import com.sofa.service.NewsService;
import com.sofa.utils.QueryInfo;
import com.sofa.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
@Api(tags = "我的收集接口")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;


    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return collectionService.delete(id);
    }


    @ApiOperation(value = "添加")
    @PostMapping("/insert")
    public Result insert(@RequestBody Collection collection){
        return collectionService.insert(collection);
    }


    @ApiOperation(value = "查询")
    @GetMapping("/findById")
    public Result findById( Long id){
        return collectionService.findById(id);
    }


}
