package com.study.seckillboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.seckillboot.model.SeckillSkuInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/12 下午5:21
 * @menu
 */
@Repository
public interface SeckillSkuInfoMapper extends BaseMapper<SeckillSkuInfo> {

    /**
     * 通过秒杀活动id查询秒杀库存商品信息
     *
     * @param secKillId
     * @return {@link List}<{@link SeckillSkuInfo}>
     */
    List<SeckillSkuInfo> querySkuInfoBySecKillId(@Param("secKillId") String secKillId);

}
