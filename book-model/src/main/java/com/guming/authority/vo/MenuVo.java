package com.guming.authority.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/11
 */
@Data
public class MenuVo implements Serializable {
    private String menuName;
    private String menuCode;
    private String menuUrl;
    private String pid;
    private String menuImg;
    private Integer menuOrder;
}
