package com.demo.project.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.demo.project.common.persistence.modal.ProjectLog;
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
 * @since 2019-08-12
 */
@Repository
public interface ProjectLogMapper extends BaseMapper<ProjectLog> {

    @Insert("INSERT INTO project_log ( project_id, user_id, date, content, pics )\n" +
            "VALUES\n" +
            "\t( #{projectId}, #{userId}, #{date}, #{content}, #{pics})")
    @Options(useGeneratedKeys=true, keyColumn="log_id",keyProperty = "log_id")
//    Integer addLog(@Param("keyword") Integer projectId,
//                   @Param("userId") Integer userId,
//                   @Param("date") Date date,
//                   @Param("content") String content,
//                   @Param("pics") String pics);
        Integer addLog(ProjectLog projectLog);

    @Select("SELECT\n" +
            "\tproject_log.log_id,\n" +
            "\twx_user.NAME,\n" +
            "\tproject_log.date,\n" +
            "\tproject_log.content,\n" +
            "\tproject_log.view_times,\n" +
            "\tproject_log.pics \n" +
            "FROM\n" +
            "\t`project_log`\n" +
            "\tJOIN wx_user ON project_log.user_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\tproject_log.project_id = #{projectId} and project_log.content REGEXP #{keyword}\n" +
            "ORDER BY\n" +
            "\tproject_log.create_time DESC")
    @Results(id="LogsResultMap",value={
            @Result(property = "logId", column = "log_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userName", column = "NAME"),
            @Result(property = "content", column = "content"),
            @Result(property = "viewTimes", column = "view_times"),
            @Result(property = "pics", column = "pics"),
    })
    List<HashMap<String,Object>> getLogs(Pagination pagination, @Param("projectId") Integer projectId, @Param("keyword") String keyword);

    @Select("SELECT\n" +
            "\tproject_log.log_id,\n" +
            "\twx_user.NAME,\n" +
            "\tproject_log.date,\n" +
            "\tproject_log.content,\n" +
            "\tproject_log.view_times,\n" +
            "\tproject_log.pics \n" +
            "FROM\n" +
            "\t`project_log`\n" +
            "\tJOIN wx_user ON project_log.user_id = wx_user.user_id \n" +
            "WHERE\n" +
            "\tproject_log.project_id = #{projectId}\n" +
            "ORDER BY\n" +
            "\tproject_log.create_time DESC")
    @Results(id="LogListResultMap",value={
            @Result(property = "logId", column = "log_id"),
            @Result(property = "date", column = "date"),
            @Result(property = "userName", column = "NAME"),
            @Result(property = "content", column = "content"),
            @Result(property = "viewTimes", column = "view_times"),
            @Result(property = "pics", column = "pics"),
    })
    List<HashMap<String,Object>> getLogList(@Param("projectId") Integer projectId);

    @Update("UPDATE project_log \n" +
            "SET view_times = view_times + 1 \n" +
            "WHERE\n" +
            "\tlog_id = #{logId}")
    Boolean updateViewTimes(@Param("logId") Integer logId);

    @Select("SELECT\n" +
            "\tproject_log.content\n" +
            "FROM\n" +
            "\t`project_log`\n" +
            "WHERE\n" +
            "\tproject_log.project_id = #{projectId}\n")
    @Results(id="LogContentsResultMap",value={
            @Result(property = "content", column = "content"),
    })
    List<String> getLogContents(@Param("projectId") Integer projectId);

    @Select("<script>SELECT \n" +
            "\tproject_log.log_id, \n" +
            "\tproject.project_name,\n" +
            "\tproject.project_id,\n" +
            "\twx_user.NAME,\n" +
            "\tauthor.name as author,\n" +
            "\tproject_log.date,\n" +
            "\tproject_log.content,\n" +
            "\tproject_log.pics,\n" +
            "\tproject_log.create_time,\n" +
            "\tproject_log.last_upd_time,\n" +
            "\tproject_log.view_times\n" +
            "FROM\n" +
            "\t`project`\n" +
            "\tJOIN wx_user ON project.leader_id = wx_user.user_id \n" +
            "\tJoin project_log on project.project_id = project_log.project_id \n" +
            "\tjoin wx_user as author on project_log.user_id = author.user_id \n" +
            "\twhere wx_user.organization_id in \n" +
            "<foreach collection='organizationIds' item='organizationId' index='index'  open = '(' close = ')' separator=','> \n" +
            "#{organizationId} \n" +
            "</foreach>\n" +
            "\tORDER BY\n" +
            "\tproject_log.create_time DESC </script> ")
    @Results(id="ProjectLogListResultMap",value={
            @Result(property = "logId", column = "log_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "leaderName", column = "NAME"),
            @Result(property = "author", column = "author"),
            @Result(property = "date", column = "date"),
            @Result(property = "content", column = "content"),
            @Result(property = "pics", column = "pics"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "lastUpdTime", column = "last_upd_time"),
            @Result(property = "viewTimes", column = "view_times"),
    })
    List<HashMap<String,Object>> getProjectLogList(@Param("organizationIds") List<Integer> organizationIds);
}
