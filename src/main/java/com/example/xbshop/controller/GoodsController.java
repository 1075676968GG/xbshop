package com.example.xbshop.controller;

import com.example.xbshop.entity.Goods;
import com.example.xbshop.entity.PageResult;
import com.example.xbshop.entity.Result;
import com.example.xbshop.service.GoodsService;
import com.example.xbshop.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liao
 * @date 2020/12/6 13:32
 * @Description
 */
@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    /**
     * 新增商品
     * @param goods
     * @return
     */

    @PostMapping
    public Result save(@RequestBody Goods goods){
        goods.setUserId(LoginUserUtil.getId());
        goods.setPublishRealName(LoginUserUtil.getLoginUser().getRealName());
        goodsService.save(goods);
        return new Result(true,"添加成功");
    }

    @PostMapping("/search/{page}")
    public Result search(@PathVariable Integer page, @RequestBody Map searchMap){
        if(searchMap.get("name")==null){
            searchMap.put("name","");
        }
        Page<Goods> goodsPage=goodsService.findPage("%"+searchMap.get("name").toString()+"%",page);

        PageResult<Goods> pageResult=new PageResult<>(goodsPage.getTotalPages(),goodsPage.getContent());

        Map returnMap=new HashMap();
        returnMap.put("pageResult",pageResult);
        returnMap.put("name",searchMap.get("name"));

        return new Result(true,"查询成功",returnMap);
    }


    @PostMapping("/{id}")
    public Result detail(@PathVariable Long id){
        Goods goods=goodsService.findById(id);
        Long userId = LoginUserUtil.getId();
        Boolean isFavorite=goodsService.isFavorite(userId,id);
        Boolean isPublish=goodsService.isPublish(LoginUserUtil.getId(),id);

        if (!isFavorite){
            goodsService.updateBrowserCount(id);
        }

        Map returnMap=new HashMap();
        returnMap.put("goods",goods);
        returnMap.put("isFavorite",isFavorite);
        returnMap.put("isPublish",isPublish);
        return new Result(true,"查询成功",returnMap);
    }

    @PostMapping("/favorite/{id}")
    public Result favorite(@PathVariable Long id) {
        // 先查询我是否收藏过这篇文章
        Boolean isFavorite = goodsService.isFavorite(LoginUserUtil.getId(), id);
        if(isFavorite){
            //来取消收藏的
            goodsService.unFavorite(LoginUserUtil.getId(),id);
            return new Result(false,"取消收藏成功");
        }else {
            //来收藏的
            goodsService.favorite(LoginUserUtil.getId(),id);
            return new Result(true,"收藏成功");
        }
    }


}
