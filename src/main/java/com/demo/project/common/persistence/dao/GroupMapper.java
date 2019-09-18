package com.demo.project.common.persistence.dao;

import com.demo.project.common.persistence.modal.Group;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-09
 */
@Repository
public interface GroupMapper extends BaseMapper<Group> {
     @Insert("<script> insert into `groups` values" +
             "<foreach collection='userIds' item='userId' index='index' separator=','>\n" +
             "(#{projectId}, #{userId})" +
             "</foreach></script>")
    Boolean addStaffs(@Param("projectId") Integer projectId, @Param("userIds") List<Integer> userIds);

     @Select("<script>" +
             "\tSELECT project_id, count(project_id) as count \n" +
             "\tfrom `groups` \n" +
             "\twhere project_id in \n" +
             "\t<foreach collection='projectIds' item='projectId' index='index'  open = '(' close = ')' separator=','> \n" +
             "#{projectId} \n" +
             "</foreach>\n" +
             "\tGROUP BY\n" +
             "\tproject_id </script>")
     @Results(id="CountResultMap",value={
             @Result(property = "projectId", column = "project_id"),
             @Result(property = "count", column = "count"),
     })
    List<HashMap<String, Object>> getStatis(@Param("projectIds") List<Integer> projectIds);

    @Select("<script>" +
            "\tSELECT groups.project_id, wx_user.name, wx_user.authority \n" +
            "\tfrom `groups` \n" +
            "\tjoin wx_user \n" +
            "\ton wx_user.user_id = groups.user_id \n" +
            "\twhere project_id in \n" +
            "\t<foreach collection='projectIds' item='projectId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{projectId} \n" +
            "</foreach> \n" +
            "</script>")
    @Results(id="UserListResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "authority", column = "authority"),
    })
    List<HashMap<String, Object>> getUsers(@Param("projectIds") List<Integer> projectIds);

}
