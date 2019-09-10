package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.Group;
import com.demo.project.common.persistence.dao.GroupMapper;
import com.demo.project.common.persistence.service.GroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-09
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    private GroupMapper groupMapper;
    @Override
    public Boolean addStaffs(Integer projectId, List<Integer> userIds) {
        return groupMapper.addStaffs(projectId,userIds);
    }
}
