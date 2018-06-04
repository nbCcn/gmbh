package com.guming.order.entity;


import com.guming.authority.entity.User;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.plans.entity.PlansPath;
import com.guming.shops.entitiy.ShopsShop;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
@Getter@Setter
@Entity
@Table(name = "sys_order_delete")
@NamedEntityGraph(name = "orderDelete.all",
        attributeNodes = {
                @NamedAttributeNode("orderTemplatesDeleteList"),
                @NamedAttributeNode("templatesType"),
        }
)
public class OrderDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "temp_type_id",insertable = false,updatable = false)
    private Long tempTypeId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "send_time")
    private Date sendTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "sort")
    private Long sort;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "content")
    private String describe;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "shop_id",insertable = false,updatable = false)
    private Long shopId;

    @Column(name = "temp_id",insertable = false,updatable = false)
    private Long tempId;

    @Column(name = "maker_id",insertable = false,updatable = false)
    private Long makerId;

    @Column(name = "plan_id",insertable = false,updatable = false)
    private Long planId;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "temp_name")
    private String tempName;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "line_name")
    private String lineName;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "line_id")
    private Long lineId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @Column(name = "shop_code")
    private String shopCode;

    @Column(name = "send_shop_id",insertable = false,updatable = false)
    private Long sendShopId;

    @Column(name = "distribution_type")
    private Integer distributionType;

    @Column(name = "distribution_phone")
    private String distribution_phone;

    @Column(name = "distribution_people")
    private String shippingPeople;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<OrderTemplatesDelete> orderTemplatesDeleteList;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "temp_type_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private TemplatesType templatesType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "temp_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Templates templates;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "shop_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private ShopsShop shopsShop;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "send_shop_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private ShopsShop sendShop;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "plan_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action=NotFoundAction.IGNORE)
    private PlansPath plansPath;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "maker_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private User user;


}
