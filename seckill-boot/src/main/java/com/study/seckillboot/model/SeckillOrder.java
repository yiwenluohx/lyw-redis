package com.study.seckillboot.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀订单表
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/11 下午7:21
 * @menu 秒杀订单表
 */
public class SeckillOrder implements Serializable {
    /**
     * 主键id
     * @return
     */
    private String id;

    /**
     * 订单号
     * @return
     */
    private String orderId;

    /**
     * 秒杀活动id
     * @return
     */
    private String seckillId;

    /**
     * 用户id
     * @return
     */
    private String userId;

    /**
     * 用户名
     * @return
     */
    private String userName;

    /**
     * 秒杀价格
     * @return
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀数量
     * @return
     */
    private Integer  seckillNum;

    /**
     * 商品id
     * @return
     */
    private String skuId;

    /**
     * 商品名字
     * @return
     */
    private String skuName;

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
     * Gets the value of orderId.
     *
     * @return the value of orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the orderId. *
     * <p>You can use getOrderId() to get the value of orderId</p>
     * * @param orderId orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     * Gets the value of userId.
     *
     * @return the value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the userId. *
     * <p>You can use getUserId() to get the value of userId</p>
     * * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the value of userName.
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName. *
     * <p>You can use getUserName() to get the value of userName</p>
     * * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Gets the value of seckillNum.
     *
     * @return the value of seckillNum
     */
    public Integer getSeckillNum() {
        return seckillNum;
    }

    /**
     * Sets the seckillNum. *
     * <p>You can use getSeckillNum() to get the value of seckillNum</p>
     * * @param seckillNum seckillNum
     */
    public void setSeckillNum(Integer seckillNum) {
        this.seckillNum = seckillNum;
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
}
