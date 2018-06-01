package com.guming.tagline.entity;


import com.guming.plans.entity.PlansPath;
import com.guming.tagwareHouse.entity.TagwareHouse;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 09:54
 */
@Data
@Entity
@Table(name = "sys_setups_tagline")
public class TagLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "ftype")
    private Integer ftype;
    @Column(name = "manager")
    private String manager;
    @Column(name = "phone")
    private String phone;
    @Column(name = "order_code")
    private Integer orderCode;
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "tag_warehouse_id",insertable = false,updatable = false)
    private Long tagWarehouseId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_warehouse_id", referencedColumnName = "id")
    private TagwareHouse tagwareHouse;

    @OneToOne(mappedBy = "tagLine")
    private PlansPath plansPath;
}
