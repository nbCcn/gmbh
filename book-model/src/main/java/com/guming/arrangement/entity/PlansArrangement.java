package com.guming.arrangement.entity;



import com.guming.plans.entity.PlansPath;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/5/2 11:25
 */
@Getter@Setter
@Entity
@Table(name = "sys_plans_arrangement")
public class PlansArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private Date day;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(
            name="sys_plans_arrangement_paths",
            joinColumns = @JoinColumn(name="arrangement_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="path_id",referencedColumnName = "id")
    )
    private List<PlansPath> plansPathList;
}
