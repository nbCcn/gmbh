package com.guming.setups.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/1
 */
@Getter
@Setter
@Entity
@Table(name = "sys_setups")
public class Setups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "config_param1")
    private String configParam1;

    @Column(name = "config_param2")
    private String configParam2;

    @Column(name = "config_param3")
    private String configParam3;

    @Column(name = "config_param4")
    private String configParam4;

    @Column(name = "config_param5")
    private String configParam5;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "code_group")
    private Integer codeGroup;
}
