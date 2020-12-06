package com.example.xbshop.dao;

import com.example.xbshop.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author liao
 * @date 2020/12/6 13:34
 * @Description
 */
public interface GoodsDao extends JpaRepository<Goods,Long> {




    Page<Goods> findByNameLikeOrderByPublishDateDesc(String name, Pageable of);

    @Query("select count(1) from Favorite f where f.uId=?1 and f.gId=?2")
    Integer countFavoriteByUserIdAndGoodsId(Long userId, Long goodsId);

    @Query("select count(1) from Goods a where a.userId=?1 and a.id=?2")
    Integer countByUserIdAndGoodsId(Long userId, Long goodsId);

    @Query("update Goods g set g.browseCount=g.browseCount+1 where g.id=?1")
    @Modifying
    void updateBrowserCount(Long id);

    @Query("delete from Favorite f where f.uId=?1 and f.gId=?2")
    @Modifying
    void deleteFavorite(Long userId, Long goodsId);

    @Query(value = "insert into favorite values(?1,?2)", nativeQuery = true)
    @Modifying
    void insertFavorite(Long userId, Long goodsId);
}
