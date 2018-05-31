package com.guming.enums;

/**
 * @Auther: Ccn
 * @Description:消费方式
 * @Date: 2018/5/30 11:50
 */
public enum ConsumeMode {
    /**
     * CONCURRENTLY
     * 使用线程池并发消费
     */
    CONCURRENTLY("CONCURRENTLY"),
    /**
     * ORDERLY
     * 单线程消费
     */
    ORDERLY("ORDERLY");

    private String mode;

    ConsumeMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
