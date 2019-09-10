package com.demo.project.common.persistence.modal;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("wx_user")
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 微信id
     */
    @TableField("open_id")
    private String openId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    @TableField("head_image")
    private String headImage;
    /**
     * 权限
     */
    private Integer authority;
    /**
     * 手机号
     */
    private String phone;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "WxUser{" +
        ", userId=" + userId +
        ", openId=" + openId +
        ", name=" + name +
        ", nickname=" + nickname +
        ", headImage=" + headImage +
        ", authority=" + authority +
        ", phone=" + phone +
        "}";
    }
}
