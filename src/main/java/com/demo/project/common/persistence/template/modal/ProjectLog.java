package com.demo.project.common.persistence.template.modal;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
    private Date date;
    /**
     * 星期
     */
    private Integer day;
    /**
     * 内容
     */
    private String content;


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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProjectLog{" +
        ", logId=" + logId +
        ", projectId=" + projectId +
        ", userId=" + userId +
        ", date=" + date +
        ", day=" + day +
        ", content=" + content +
        "}";
    }
}
