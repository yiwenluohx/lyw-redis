package com.study.seckillboot.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀活动
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/9/11 下午7:15
 * @menu 秒杀活动
 */
public class SeckillInfo implements Serializable {
    /**
     * 主键id
     *
     * @return
     */
    private String id;

    /**
     * 活动名称
     *
     * @return
     */
    private String name;

    /**
     * 开始时间
     *
     * @return
     */
    private Date startTime;

    /**
     * 结束时间
     *
     * @return
     */
    private Date endTime;

    /**
     * 描述
     *
     * @return
     */
    private String description;

    /**
     * 活动状态，1 开启 2 关闭
     *
     * @return
     */
    private Integer state;

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
     * Gets the value of name.
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name. *
     * <p>You can use getName() to get the value of name</p>
     * * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of startTime.
     *
     * @return the value of startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime. *
     * <p>You can use getStartTime() to get the value of startTime</p>
     * * @param startTime startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the value of endTime.
     *
     * @return the value of endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the endTime. *
     * <p>You can use getEndTime() to get the value of endTime</p>
     * * @param endTime endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the value of description.
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description. *
     * <p>You can use getDescription() to get the value of description</p>
     * * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value of state.
     *
     * @return the value of state
     */
    public Integer getState() {
        return state;
    }

    /**
     * Sets the state. *
     * <p>You can use getState() to get the value of state</p>
     * * @param state state
     */
    public void setState(Integer state) {
        this.state = state;
    }
}
