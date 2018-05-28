package com.guming.service.setups.impl;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ConfigConstants;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.dao.setups.SetupsRepository;
import com.guming.service.setups.SetupsService;
import com.guming.setups.dto.BasicConfigDto;
import com.guming.setups.entity.Setups;
import com.guming.setups.vo.BasicConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
@Service
public class SetupsServiceImpl extends BaseServiceImpl implements SetupsService {

    @Autowired
    private SetupsRepository setupsRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.setupsRepository;
    }

    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public ResponseParam<BasicConfigVo> findBasicConfig() {
        List<Setups> setupsList =setupsRepository.findAllByCodeAndIsValid(ConfigConstants.BASIC_CONFIG_CODE,true);

        BasicConfigVo basicConfigVo = new BasicConfigVo();
        if (setupsList != null && !setupsList.isEmpty()){
            for (Setups setups : setupsList){
                if (setups.getCodeGroup().equals(1)){
                    basicConfigVo.setCanPrepareOrdered(setups.getConfigParam1().trim().equals("1")?true:false);
                    basicConfigVo.setCanSpecialOrdered(setups.getConfigParam2().trim().equals("1")?true:false);
                    basicConfigVo.setOrderCycle(Integer.parseInt(setups.getConfigParam3()));
                    basicConfigVo.setOrderDay(Integer.parseInt(setups.getConfigParam4()));
                    basicConfigVo.setCanSupplement(setups.getConfigParam5().trim().equals("1")?true:false);
                }else if (setups.getCodeGroup().equals(2)){
                    basicConfigVo.setCanChecked(setups.getConfigParam1().trim().equals("1")?true:false);
                    basicConfigVo.setAdvanceStartDay(Integer.parseInt(setups.getConfigParam2()));
                    basicConfigVo.setAdvanceEndDay(Integer.parseInt(setups.getConfigParam3()));
                    basicConfigVo.setThirdUrl(setups.getConfigParam4());
                }
            }
        }
        return ResponseParam.success(basicConfigVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateBasicConfig(BasicConfigDto basicConfigDto) {
        if (basicConfigDto != null){
            List<Setups> setupsList =setupsRepository.findAllByCodeAndIsValid(ConfigConstants.BASIC_CONFIG_CODE,true);
            if (setupsList != null && !setupsList.isEmpty()) {
                for (Setups setups : setupsList) {
                    if (setups.getCodeGroup().equals(1)){
                        setups.setConfigParam1(basicConfigDto.getCanPrepareOrdered()?"1":"0");
                        setups.setConfigParam2(basicConfigDto.getCanSpecialOrdered()?"1":"0");
                        if (basicConfigDto.getOrderCycle() != null) {
                            setups.setConfigParam3(basicConfigDto.getOrderCycle().toString());
                        }
                        if (basicConfigDto.getOrderDay() != null) {
                            setups.setConfigParam4(basicConfigDto.getOrderDay().toString());
                        }
                        setups.setConfigParam5(basicConfigDto.getCanSupplement()?"1":"0");
                    }else if (setups.getCodeGroup().equals(2)){
                        setups.setConfigParam1(basicConfigDto.getCanChecked()?"1":"0");
                        if (basicConfigDto.getAdvanceStartDay() == null ||  basicConfigDto.getAdvanceEndDay() ==null){
                            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUP_ADVANCE_DAY_EMPTY);
                        }
                        if (basicConfigDto.getAdvanceEndDay() >= basicConfigDto.getAdvanceStartDay()){
                            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_SETUP_ADVANCE_START_END_NOT_MATCH);
                        }
                        setups.setConfigParam2(basicConfigDto.getAdvanceStartDay().toString());
                        setups.setConfigParam3(basicConfigDto.getAdvanceEndDay().toString());
                        setups.setConfigParam4(basicConfigDto.getThirdUrl());
                    }
                }
            }
            setupsRepository.save(setupsList);
        }
        return getSuccessOperationResult();
    }
}
