package com.guming.authority.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/16
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user_ding")
public class UserDing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "ding_user")
    private String dingUser;

    @Column(name = "ding_id")
    private String dingId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT),insertable = false,updatable = false)
    private User user;
}
