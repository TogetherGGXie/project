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
 * @since 2019-08-29
 */
@TableName("administrator")
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员姓名
     */
    @TableId("admin_name")
    private String adminName;
    /**
     * 密码
     */
    private String password;


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator{" +
        ", adminName=" + adminName +
        ", password=" + password +
        "}";
    }
}
