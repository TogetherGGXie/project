package com.demo.project.common.persistence.modal;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-08-12
 */
@TableName("project_log")
public class ProjectLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;
    /**
     * 项目id
     */
    @TableField("project_id")
    private Integer projectId;
    /**
     * 编写者
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 日志日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片
     */
    private String pics;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 最近更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM- HH:mm:ss")
    @TableField("last_upd_time")
    private Date lastUpdTime;

    /**
     * 浏览次数
     */
    @TableField("view_times")
    private Integer viewTimes;

    public Integer getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(Integer viewTimes) {
        this.viewTimes = viewTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Date lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    @Override
    public String toString() {
        return "ProjectLog{" +
                "logId=" + logId +
                ", projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", pics='" + pics + '\'' +
                ", createTime=" + createTime +
                ", lastUpdTime=" + lastUpdTime +
                ", viewTimes=" + viewTimes +
                '}';
    }
}
