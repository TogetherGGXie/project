package com.demo.project.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.demo.project.common.persistence.template.modal.Project;
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
            "\twx_user.NAME REGEXP #{keyword} or project.project_name REGEXP #{keyword}" +
            "ORDER BY\n" +
            "\tproject.start_time DESC")
    @Results(id="ProjectsResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "leaderName", column = "NAME"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "img", column = "img"),
    })
    List<HashMap<String,Object>> getProjects(Pagination pagination, @Param("keyword") String keyword);

    @Select("SELECT\n" +
            "\tproject.project_id,\n"+
            "\tproject.project_name\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\t ORDER BY\n" +
            "\tproject.start_time DESC")
    @Results(id="ProjectNamesResultMap",value={
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
    })
    List<HashMap<String,Object>> getProjectNames();
}
