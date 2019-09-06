package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.template.modal.Group;
import com.demo.project.common.persistence.dao.GroupMapper;
import com.demo.project.common.persistence.service.GroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-06
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

}
