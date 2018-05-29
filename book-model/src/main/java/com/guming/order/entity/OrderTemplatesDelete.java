package com.guming.order.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
@Getter
@Setter
@Entity
@Table(name = "sys_order_templates_delete")
public class OrderTemplatesDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "pre_edited")
    private Integer preEdited;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "spec")
    private String spec;

    @Column(name = "unit")
    private String unit;

    @Column(name = "stock")
    private Integer stock;
}
