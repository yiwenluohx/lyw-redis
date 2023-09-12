package com.study.seckillboot.model;

import java.io.Serializable;

/**
 * 秒杀实体
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/7 下午2:52
 * @menu 秒杀实体
 */
public class MiaoShaGoods implements Serializable {
    private Integer id;

    private String goodsName;

    private Integer goodsSum;

    private Integer version;

    /**
     * Gets the value of id.
     *
     * @return the value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id. *
     * <p>You can use getId() to get the value of id</p>
     * * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the value of goodsName.
     *
     * @return the value of goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Sets the goodsName. *
     * <p>You can use getGoodsName() to get the value of goodsName</p>
     * * @param goodsName goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * Gets the value of goodsSum.
     *
     * @return the value of goodsSum
     */
    public Integer getGoodsSum() {
        return goodsSum;
    }

    /**
     * Sets the goodsSum. *
     * <p>You can use getGoodsSum() to get the value of goodsSum</p>
     * * @param goodsSum goodsSum
     */
    public void setGoodsSum(Integer goodsSum) {
        this.goodsSum = goodsSum;
    }

    /**
     * Gets the value of version.
     *
     * @return the value of version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the version. *
     * <p>You can use getVersion() to get the value of version</p>
     * * @param version version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
