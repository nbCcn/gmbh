package com.guming.service.system.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.service.system.SystemService;
import org.springframework.stereotype.Service;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/10
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl implements SystemService {

    @Override
    protected BaseRepository getRepository() {
        return null;
    }

    @Override
    public String getVersion(){
        return bookConfig.getSystemVersion();
    }

    @Override
    public String getContactNumber(){
        return bookConfig.getPhone();
    }
}
