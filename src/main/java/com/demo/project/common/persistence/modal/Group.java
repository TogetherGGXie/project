package com.demo.project.common.persistence.modal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-09
 */
@TableName("groups")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableId("project_id")
    @TableField("project_id")
    private Integer projectId;
    /**
     * 用户id
     */
    @TableId("user_id")
    @TableField("user_id")
    private Integer userId;


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

    @Override
    public String toString() {
        return "Group{" +
        ", projectId=" + projectId +
        ", userId=" + userId +
        "}";
    }
}
