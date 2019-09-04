package com.demo.project.common.persistence.template.modal;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
@TableName("view_statis")
public class ViewStatis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId("log_id")
    private Integer logId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 查看日志时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("view_time")
    private Date viewTime;


    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    @Override
    public String toString() {
        return "ViewStatis{" +
        ", logId=" + logId +
        ", userId=" + userId +
        ", viewTime=" + viewTime +
        "}";
    }
}
