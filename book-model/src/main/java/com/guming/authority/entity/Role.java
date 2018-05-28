package com.guming.authority.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/11
 */
@Data
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name",unique = true)
    private String roleName;

    @Column(name = "role_level")
    private Integer roleLevel;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @ManyToMany(mappedBy = "roleList",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<User> userList;
}
