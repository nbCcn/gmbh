package com.guming.shops.entitiy;



import com.guming.authority.entity.User;
import com.guming.plans.entity.Pathshop;
import com.guming.tagrank.entity.TagRank;
import com.guming.tagwareHouse.entity.TagwareHouse;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: Ccn
 * @Description:商铺实体
 * @Date: 2018/4/12 14:41
 */
@Entity
@Table(name = "sys_shops_shop")
public class ShopsShop implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String province;
    private String city;
    private String district;
    private String address;
    private Double lng;
    private Double lat;
    private String contact;
    private String phone;
    private Integer status;
    private Date joinedTime;
    private Date createdTime;
    private Date updatedTime;
    private Boolean isDeleted;

    //店铺 仓库 关系映射
    private Set<TagwareHouse> tagwareHouseSet;

    private List<User> userList;

    private Pathshop pathshop;

    private TagRank tagRank;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "joined_time")
    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    @Column(name = "created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "updated_time")
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_shops_shop_tag_warehouses",
            joinColumns = @JoinColumn(name = "shop_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "tagwarehouse_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    )
    public Set<TagwareHouse> getTagwareHouseSet() {
        return tagwareHouseSet;
    }

    public void setTagwareHouseSet(Set<TagwareHouse> tagwareHouseSet) {
        this.tagwareHouseSet = tagwareHouseSet;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "sys_shops_users",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    )
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @OneToOne(mappedBy = "shopsShop", fetch = FetchType.LAZY)
    public Pathshop getPathshop() {
        return pathshop;
    }

    public void setPathshop(Pathshop pathshop) {
        this.pathshop = pathshop;
    }

    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_rank_id")
    public TagRank getTagRank() {
        return tagRank;
    }

    public void setTagRank(TagRank tagRank) {
        this.tagRank = tagRank;
    }
}
