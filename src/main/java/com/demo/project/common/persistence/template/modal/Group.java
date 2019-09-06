package com.demo.project.common.persistence.template.modal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-06
 */
@TableName("group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableField("project_id")
    private Integer projectId;
    /**
     * 用户id
     */
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
