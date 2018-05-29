package com.guming.order.entity;

import com.guming.products.entity.Products;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
@Getter
@Setter
@Entity
@Table(name = "sys_order_templates_submission")
@NamedEntityGraph(
        name = "orderTemplatesSubmission.all",
        attributeNodes = {
                @NamedAttributeNode("products")
        }
)
public class OrderTemplatesSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false,updatable = false,foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private Products products;
}
