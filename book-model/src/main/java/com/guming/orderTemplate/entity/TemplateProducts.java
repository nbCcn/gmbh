package com.guming.orderTemplate.entity;

import com.guming.products.entity.Products;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: PengCheng
 * @Description: 订单模板拥有的商品数量关联表
 * @Date: 2018/4/20
 */
@Entity
@Table(name = "sys_template_products")
@Getter
@Setter
public class TemplateProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_id",insertable = false,updatable = false)
    private Long templateId;

    @Column(name = "product_count")
    private Integer productCount;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "product_id",insertable = false,updatable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id", referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "template_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Templates templates;
}
