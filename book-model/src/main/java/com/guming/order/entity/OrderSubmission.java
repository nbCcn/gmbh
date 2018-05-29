package com.guming.order.entity;

import com.guming.authority.entity.User;
import com.guming.orderTemplate.entity.Templates;
import com.guming.orderTemplate.entity.TemplatesType;
import com.guming.plans.entity.PlansPath;
import com.guming.shops.entitiy.ShopsShop;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
@Getter
@Setter
@Entity
@Table(name = "sys_order_submission")
@NamedEntityGraph(
        name = "orderSubmission.all",
        attributeNodes = {
                @NamedAttributeNode(value = "orderTemplatesSubmissionList",
                    subgraph = "orderTemplatesSubmission.products"),
                @NamedAttributeNode("templatesType"),
                @NamedAttributeNode("templates"),
                @NamedAttributeNode("shopsShop"),
                @NamedAttributeNode("plansPath"),
                @NamedAttributeNode("sendShop")
        },
        subgraphs = {//subgraphs 来定义关联对象的属性
                @NamedSubgraph(name = "orderTemplatesSubmission.products",//一层延伸
                        attributeNodes = @NamedAttributeNode("products"))
        }
)
public class OrderSubmission {
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

    @Column(name = "shop_id",updatable = false,insertable = false)
    private Long shopId;

    @Column(name = "temp_id",updatable = false,insertable = false)
    private Long tempId;

    @Column(name = "maker_id",insertable = false,updatable = false)
    private Long makerId;

    @Column(name = "plan_id",insertable = false,updatable = false)
    private Long planId;

    @Column(name = "send_shop_id",insertable = false,updatable = false)
    private Long sendShopId;

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

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "order_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<OrderTemplatesSubmission> orderTemplatesSubmissionList;
}
