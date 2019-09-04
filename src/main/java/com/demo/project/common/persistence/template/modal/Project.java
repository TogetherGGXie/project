package com.demo.project.common.persistence.template.modal;

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
@TableName("project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工程id
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;
    /**
     * 工程名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 负责人id
     */
    @TableField("leader_id")
    private Integer leaderId;
    /**
     * 开始日期
     */
    @TableField("start_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 竣工日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("end_time")
    private Date endTime;
    /**
     * 图片
     */
    private String img;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", leaderId=" + leaderId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", lastUpdTime=" + lastUpdTime +
                '}';
    }
}
