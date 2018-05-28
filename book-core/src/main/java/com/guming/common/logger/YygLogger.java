package com.guming.common.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/22
 */
public class YygLogger{
    private static final Level OPERATE = Level.getLevel("OPERATE");

    private static final Level KINGDEE = Level.getLevel("KINGDEE");

    private static final Level DINGTALK = Level.getLevel("DINGTALK");

    private Logger logger;

    public YygLogger(Class clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public void operate(String message){
        logger.log(OPERATE,message);
    }

    public void kingdee(String message) {
        logger.log(KINGDEE,message);
    }

    public void dingtalk(String message) {logger.log(DINGTALK,message);}

    public void error(String message,Throwable e) {
        logger.log(Level.ERROR,message,e);
    }

    public void error(String message) {
        logger.log(Level.ERROR,message);
    }
}
