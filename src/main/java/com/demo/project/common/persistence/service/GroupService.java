package com.demo.project.common.persistence.service;

import com.demo.project.common.persistence.modal.Group;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-09
 */
public interface GroupService extends IService<Group> {
    Boolean addStaffs(Integer projectId, List<Integer> userIds);
    List<HashMap<String,Object>> getStatis(List<Integer> projectIds);
    HashMap<String, List<HashMap<String, Object>>> getUsers(List<Integer> projectIds);
}
