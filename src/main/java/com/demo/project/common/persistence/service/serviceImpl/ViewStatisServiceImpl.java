package com.demo.project.common.persistence.service.serviceImpl;

import com.demo.project.common.persistence.modal.ViewStatis;
import com.demo.project.common.persistence.dao.ViewStatisMapper;
import com.demo.project.common.persistence.service.ViewStatisService;
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
 * @since 2019-08-29
 */
@Service
public class ViewStatisServiceImpl extends ServiceImpl<ViewStatisMapper, ViewStatis> implements ViewStatisService {

    @Autowired
    private ViewStatisMapper viewStatisMapper;
    @Override
    public List<HashMap<String, Object>> getViewHistory(Integer logId) {
        return viewStatisMapper.getViewHistory(logId);
    }
}
