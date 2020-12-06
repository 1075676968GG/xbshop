package com.example.xbshop.service;

import com.example.xbshop.dao.GoodsDao;
import com.example.xbshop.entity.Goods;
import com.example.xbshop.entity.PageResult;
import com.example.xbshop.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liao
 * @date 2020/12/6 13:34
 * @Description
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    @Autowired
    GoodsMapper goodsMapper;

    public void save(Goods goods) {
        goodsDao.save(goods);
    }

    public Page<Goods> findPage(String name, Integer page) {
        return goodsDao.findByNameLikeOrderByPublishDateDesc(name, PageRequest.of(page - 1, PageResult.PAGE_SIZE));
    }

    public Goods findById(Long id) {
        return goodsDao.findById(id).get();
    }

    public Boolean isFavorite(Long userId, Long goodsId) {
        return goodsDao.countFavoriteByUserIdAndGoodsId(userId,goodsId) > 0 ? true:false;
    }

    public Boolean isPublish(Long userId, Long goodsId) {
        return goodsDao.countByUserIdAndGoodsId(userId, goodsId) > 0 ? true : false;
    }

    @Transactional
    public void updateBrowserCount(Long id) {
        goodsDao.updateBrowserCount(id);
    }

    @Transactional
    public void unFavorite(Long userId, Long goodsId) {
        goodsDao.deleteFavorite(userId,goodsId);
    }

    @Transactional
    public void favorite(Long userId, Long goodsId) {
        goodsDao.insertFavorite(userId,goodsId);
    }
}
