package com.demo.project.common.persistence.service;

import com.demo.project.common.persistence.modal.Group;
import com.baomidou.mybatisplus.service.IService;

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
}
