package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.Administrator;
import com.demo.project.common.persistence.dao.AdministratorMapper;
import com.demo.project.common.persistence.service.AdministratorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XieZhiyang123
 * @since 2019-08-29
 */
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {

}
