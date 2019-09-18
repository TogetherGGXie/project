package com.demo.project.common.persistence.dao;

import com.demo.project.common.persistence.modal.WxUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Repository
public interface WxUserMapper extends BaseMapper<WxUser> {

    @Select("<script> SELECT wx_user.user_id, wx_user.name, wx_user.authority, organization.name as organizationName, wx_user.status from \n" +
            "wx_user join organization on organization.id = wx_user.organization_id \n" +
            "where wx_user.organization_id in \n" +
            "<foreach collection='organizationIds' item='organizationId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{organizationId} \n" +
            "</foreach></script>")
    @Results(id="WxUserListResultMap",value={
            @Result(property = "userId", column = "user_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "authority", column = "authority"),
            @Result(property = "organizationName", column = "organizationName"),
            @Result(property = "status", column = "status"),
    })
    public List<HashMap<String, Object>> getUsers(@Param("organizationIds") List<Integer> organizationIds);
}
