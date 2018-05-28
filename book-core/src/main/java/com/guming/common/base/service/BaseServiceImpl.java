package com.guming.common.base.service;

import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.config.BookConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 15:33 2018/4/10/010
 */
public abstract class BaseServiceImpl implements BaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected BookConfig bookConfig;

    protected String i18nHandler(String i18nMsg,String ... text){
        String msg = messageSource.getMessage(i18nMsg.trim(), null, LocaleContextHolder.getLocale());
        if (text!=null && text.length>0){
            for (int i=0;i<text.length;i++){
                msg = msg.replace("{"+i+"}",text[i]);
            }
        }
        return msg;
    }

    protected abstract BaseRepository getRepository();

    protected Logger getLogger() {
        return logger;
    }

    protected ResponseParam getSuccessOperationResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessAddResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_ADD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessUpdateResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_UPDATE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessDeleteResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_DELETE_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessImportResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_UPLOAD_SUCCESS_MSG));
    }

    protected ResponseParam getSuccessExportResult() {
        return ResponseParam.success(null, i18nHandler(ErrorMsgConstants.OPTION_EXPORT_SUCCESS_MSG));
    }

    @Override
    public void importExcel(MultipartFile multipartFile){}
}
