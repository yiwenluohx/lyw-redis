package com.study.seckillboot.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀商品及相关规则信息
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/11 下午7:10
 * @menu 秒杀商品及相关规则信息
 */
public class SeckillSkuInfo implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 秒杀活动id
     */
    private String seckillId;

    /**
     * 商品id
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String skuName;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀总数量（库存
     */
    private Integer seckillStock;

    /**
     * 每人限购数量
     */
    private Integer limitNum;

    /**
     * Gets the value of id.
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id. *
     * <p>You can use getId() to get the value of id</p>
     * * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the value of seckillId.
     *
     * @return the value of seckillId
     */
    public String getSeckillId() {
        return seckillId;
    }

    /**
     * Sets the seckillId. *
     * <p>You can use getSeckillId() to get the value of seckillId</p>
     * * @param seckillId seckillId
     */
    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    /**
     * Gets the value of skuId.
     *
     * @return the value of skuId
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * Sets the skuId. *
     * <p>You can use getSkuId() to get the value of skuId</p>
     * * @param skuId skuId
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    /**
     * Gets the value of skuName.
     *
     * @return the value of skuName
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * Sets the skuName. *
     * <p>You can use getSkuName() to get the value of skuName</p>
     * * @param skuName skuName
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    /**
     * Gets the value of seckillPrice.
     *
     * @return the value of seckillPrice
     */
    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    /**
     * Sets the seckillPrice. *
     * <p>You can use getSeckillPrice() to get the value of seckillPrice</p>
     * * @param seckillPrice seckillPrice
     */
    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    /**
     * Gets the value of seckillStock.
     *
     * @return the value of seckillStock
     */
    public Integer getSeckillStock() {
        return seckillStock;
    }

    /**
     * Sets the seckillStock. *
     * <p>You can use getSeckillStock() to get the value of seckillStock</p>
     * * @param seckillStock seckillStock
     */
    public void setSeckillStock(Integer seckillStock) {
        this.seckillStock = seckillStock;
    }

    /**
     * Gets the value of limitNum.
     *
     * @return the value of limitNum
     */
    public Integer getLimitNum() {
        return limitNum;
    }

    /**
     * Sets the limitNum. *
     * <p>You can use getLimitNum() to get the value of limitNum</p>
     * * @param limitNum limitNum
     */
    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }
}
