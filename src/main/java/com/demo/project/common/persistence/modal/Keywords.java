package com.demo.project.common.persistence.modal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-03
 */
@TableName("keywords")
public class Keywords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableId("project_id")
    private Integer projectId;
    /**
     * 关键字
     */
    private String keywords;


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Keywords{" +
        ", projectId=" + projectId +
        ", keywords=" + keywords +
        "}";
    }
}
