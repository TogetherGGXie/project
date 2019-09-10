package com.demo.project.common.persistence.dao;

import com.demo.project.common.persistence.modal.Group;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
