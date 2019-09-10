package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.Organization;
import com.demo.project.common.persistence.dao.OrganizationMapper;
import com.demo.project.common.persistence.service.OrganizationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-09-09
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

}
