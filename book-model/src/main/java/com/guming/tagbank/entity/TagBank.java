package com.guming.tagbank.entity;


import com.guming.tagwareHouse.entity.TagwareHouse;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/6/5 13:49
 */
@Entity
@Table(name = "sys_setups_tagbank")
public @Data
class TagBank {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "num")
    private String num;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "branch")
    private String branch;
    @Basic
    @Column(name = "owner")
    private String owner;
    @Basic
    @Column(name = "created_time")
    private Date createdTime;
    @Basic
    @Column(name = "updated_time")
    private Date updatedTime;


    @ManyToMany(mappedBy = "tagBankList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagwareHouse> tagwareHouseList;

}
