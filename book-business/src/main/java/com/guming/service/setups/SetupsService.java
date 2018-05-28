package com.guming.service.setups;


import com.guming.common.base.service.BaseService;
import com.guming.common.base.vo.ResponseParam;
import com.guming.setups.dto.BasicConfigDto;
import com.guming.setups.vo.BasicConfigVo;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
public interface SetupsService extends BaseService {

    /**
     * 查询基础配置参数
     * @return
     */
    ResponseParam<BasicConfigVo> findBasicConfig();

    ResponseParam updateBasicConfig(BasicConfigDto basicConfigDto);
}
