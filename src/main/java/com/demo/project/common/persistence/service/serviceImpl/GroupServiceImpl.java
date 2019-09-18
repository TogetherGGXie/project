package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.Group;
import com.demo.project.common.persistence.dao.GroupMapper;
import com.demo.project.common.persistence.service.GroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public List<HashMap<String, Object>> getStatis(List<Integer> projectIds) {
        return groupMapper.getStatis(projectIds);
    }

    @Override
    public HashMap<String, List<HashMap<String, Object>>> getUsers(List<Integer> projectIds) {
        HashMap<String, List<HashMap<String, Object>>> result = new HashMap<>();
        List<HashMap<String, Object>> users = groupMapper.getUsers(projectIds);
        for (HashMap<String, Object> user : users) {
            if (result.get(user.get("projectId").toString()) == null) {
                List<HashMap<String, Object>> userList = new ArrayList<>();
                userList.add(user);
                result.put(user.get("projectId").toString(),userList);
            } else {
                List<HashMap<String, Object>> userList= result.get(user.get("projectId").toString());
                userList.add(user);
                result.put(user.get("projectId").toString(),userList);
            }
        }
        return result;
    }
}
