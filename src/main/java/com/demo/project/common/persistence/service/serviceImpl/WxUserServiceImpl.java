package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.WxUser;
import com.demo.project.common.persistence.dao.WxUserMapper;
import com.demo.project.common.persistence.service.WxUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-12
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Autowired
    private WxUserMapper wxUserMapper;
    @Override
    public List<HashMap<String, Object>> getUserList(List<Integer> organizationIds) {
        return wxUserMapper.getUsers(organizationIds);
    }

    @Override
    public List<HashMap<String, Object>> getAdminList(List<Integer> organizationIds) {
        return wxUserMapper.getAdmins(organizationIds);
    }

    @Override
    public HashMap<String, Object> getUserInfo(Integer userId) {
        return wxUserMapper.getUserInfo(userId);
    }

    @Override
    public List<HashMap<String, Object>> getUserSelection(List<Integer> oids) {
        return wxUserMapper.getUserSelection(oids);
    }
}
