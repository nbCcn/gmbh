package com.guming.authority.entity;

import com.guming.common.base.entity.BaseEntity;
import com.guming.shops.entitiy.ShopsShop;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description: 用户
 * @Date: 2018/4/11
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "is_staff")
    private Boolean isStaff;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_superuser")
    private Boolean isSuperuser;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "joined_time")
    private Date joinedTime;

    @Column(name = "phone")
    private String phone;

    @Column(name = "init_pass")
    private String initPass;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "user",orphanRemoval = true)
    private List<UserDing> userDingList;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))}
    )
    private List<Role> roleList;

    @ManyToMany(mappedBy = "userList", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<ShopsShop> shopsShops;
}
