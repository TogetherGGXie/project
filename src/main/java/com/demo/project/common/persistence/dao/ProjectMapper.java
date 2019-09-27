package com.demo.project.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.demo.project.common.persistence.modal.Project;
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
public interface ProjectMapper extends BaseMapper<Project> {

    @Select("SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name,\n" +
            "\twx_user.NAME,\n" +
            "\tproject.start_time,\n" +
            "\tproject.end_time,\n" +
            "\tproject.img \n" +
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\t(wx_user.NAME REGEXP #{keyword} or project.project_name REGEXP #{keyword}) \n" +
            "\tand project.project_id in (SELECT project_id FROM groups where groups.user_id = #{userId}) \n" +
            "\tor project.leader_id = #{userId} \n" +
            "\tORDER BY\n" +
            "\tproject.create_time DESC")
    @Results(id="ProjectsResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "leaderName", column = "NAME"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "img", column = "img"),
    })
    List<HashMap<String,Object>> getProjects(Pagination pagination, @Param("keyword") String keyword, @Param("userId") Integer userId);


    @Select("SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name,\n" +
            "\twx_user.NAME,\n" +
            "\tproject.introduction,\n" +
            "\tproject.start_time,\n" +
            "\tproject.end_time,\n" +
            "\tproject.img,\n" +
            "\tkeywords.keywords\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "\tLEFT JOIN keywords ON project.project_id = keywords.project_id \n" +
            "WHERE\n" +
            "\tproject.project_id = #{projectId}")
    @Results(id="ProjectResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "introduction", column = "introduction"),
            @Result(property = "leaderName", column = "NAME"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "img", column = "img"),
            @Result(property = "keywords", column = "keywords"),
    })
    HashMap<String,Object> getProject(@Param("projectId") Integer projectId);


    @Select("SELECT\n" +
            "\twx_user.organization_id\n"+
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\tproject.project_id = #{projectId}")
    @Results(id="OrganizationIdResultMap",value={
            @Result(property = "organizationId", column = "organization_id"),
    })
    Integer getOrganizationId(@Param("projectId") Integer projectId);

    @Select("<script>SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name,\n" +
            "\twx_user.NAME,\n" +
            "\tproject.introduction,\n" +
            "\tproject.start_time,\n" +
            "\tproject.end_time,\n" +
            "\tproject.img,\n" +
            "\tproject.create_time,\n" +
            "\tproject.last_upd_time,\n" +
            "\tkeywords.keywords\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "\tLeft JOIN keywords on project.project_id = keywords.project_id \n" +
            "\twhere wx_user.organization_id in \n" +
            "<foreach collection='organizationIds' item='organizationId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{organizationId} \n" +
            "</foreach>\n" +
            "\tORDER BY\n" +
            "\tproject.create_time DESC </script> ")
    @Results(id="ProjectListResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "introduction", column = "introduction"),
            @Result(property = "leaderName", column = "NAME"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "img", column = "img"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastUpdTime", column = "last_upd_time"),
            @Result(property = "keywords", column = "keywords"),
    })
    List<HashMap<String,Object>> getProjectList(@Param("organizationIds") List<Integer> organizationIds);

    @Select("<script>SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name,\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "\twhere wx_user.organization_id in \n" +
            "<foreach collection='organizationIds' item='organizationId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{organizationId} \n" +
            "</foreach>\n" +
            "\tORDER BY\n" +
            "\tproject.create_time DESC </script> ")
    @Results(id="ProjectSelectionResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
    })
    List<HashMap<String,Object>> getProjectSelection(@Param("organizationIds") List<Integer> organizationIds);

    @Select("<script>SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\twhere project.leader_id = #{userId} \n" +
            "\tor project.project_id in \n" +
            "<foreach collection='projectIds' item='projectId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{projectId} \n" +
            "</foreach>\n" +
            "\t ORDER BY\n" +
            "\tproject.create_time DESC</script>")
    @Results(id="ProjectNamesResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
    })
    List<HashMap<String,Object>> getProjectNames(@Param("projectIds") List<Integer> projectIds, @Param("userId") Integer userId);
}
