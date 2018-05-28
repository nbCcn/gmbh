package com.guming.order.entity;


import com.guming.orderTemplate.entity.TemplatesType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/27
 */
@Entity
@Table(name = "sys_order_finish")
@NamedEntityGraph(name = "orderFinish.all",
        attributeNodes = {
                @NamedAttributeNode("orderTemplatesFinishList"),
                @NamedAttributeNode("templatesType"),
        }
)
public class OrderFinish {
    private Long id;
    private String code;
    private Long tempTypeId;
    private Integer status;
    private Date sendTime;
    private BigDecimal totalPrice;
    private Long sort;
    private Boolean isValid;
    private String describe;
    private Date createTime;
    private Date updateTime;
    private Long shopId;
    private Long tempId;
    private Long makerId;
    private Long planId;
    private String shopName;
    private String tempName;
    private String warehouseName;
    private String lineName;
    private Long warehouseId;
    private Long lineId;
    private String userName;
    private String firstName;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String address;
    private String shopCode;
    private Long sendShopId;
    private Integer distributionType;
    private List<OrderTemplatesFinish> orderTemplatesFinishList;

    private TemplatesType templatesType;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "temp_type_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public TemplatesType getTemplatesType() {
        return templatesType;
    }

    public void setTemplatesType(TemplatesType templatesType) {
        this.templatesType = templatesType;
    }

    @Column(name = "distribution_type")
    public Integer getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(Integer distributionType) {
        this.distributionType = distributionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name = "temp_type_id",insertable = false,updatable = false)
    public Long getTempTypeId() {
        return tempTypeId;
    }

    public void setTempTypeId(Long tempTypeId) {
        this.tempTypeId = tempTypeId;
    }

    
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
    @Column(name = "send_time")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    
    @Column(name = "sort")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    
    @Column(name = "is_valid")
    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    
    @Column(name = "content")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    
    @Column(name = "temp_id")
    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    
    @Column(name = "maker_id")
    public Long getMakerId() {
        return makerId;
    }

    public void setMakerId(Long makerId) {
        this.makerId = makerId;
    }

    
    @Column(name = "plan_id")
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    
    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    
    @Column(name = "temp_name")
    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    
    @Column(name = "warehouse_name")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    
    @Column(name = "line_name")
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    
    @Column(name = "warehouse_id")
    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    
    @Column(name = "line_id")
    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    @Column(name = "district")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name = "shop_code")
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    
    @Column(name = "send_shop_id")
    public Long getSendShopId() {
        return sendShopId;
    }

    public void setSendShopId(Long sendShopId) {
        this.sendShopId = sendShopId;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    public List<OrderTemplatesFinish> getOrderTemplatesFinishList() {
        return orderTemplatesFinishList;
    }

    public void setOrderTemplatesFinishList(List<OrderTemplatesFinish> orderTemplatesFinishList) {
        this.orderTemplatesFinishList = orderTemplatesFinishList;
    }
}
