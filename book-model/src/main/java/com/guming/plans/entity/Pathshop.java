package com.guming.plans.entity;


import com.guming.shops.entitiy.ShopsShop;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/2
 */
@Data
@Entity
@Table(name = "sys_plans_pathshop")
public class Pathshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_id",insertable = false,updatable = false)
    private Long pathId;

    @Column(name = "shop_id")
    private Long shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private PlansPath plansPath;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "shop_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ShopsShop shopsShop;
}
