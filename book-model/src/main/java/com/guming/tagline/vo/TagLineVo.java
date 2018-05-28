package com.guming.tagline.vo;
import com.guming.common.utils.DateUtil;
import com.guming.tagwareHouse.vo.TagwareHouseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 09:54
 */
@Data
@ApiModel(value = "TagLineVo", description = "返回的送货路线信息实体")
public class TagLineVo {
    @ApiModelProperty(value = "送货路线Id")
    private Long id;
    @ApiModelProperty(value = "路线名称")
    private String name;
    @ApiModelProperty(value = "物流状态")
    private Integer ftype;
    @ApiModelProperty(value = "负责人")
    private String manager;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "排序码")
    private Integer orderCode;

    @ApiModelProperty(value = "创建时间")
    private String createdTimeStr;
    private Date createdTime;

    @ApiModelProperty(value = "修改时间")
    private String updatedTimeStr;
    private Date updatedTime;
    // 仓库Vo对象
    @ApiModelProperty(value = "仓库对象")
    private TagwareHouseVo tagwareHouseVo;


    public String getCreatedTimeStr() {
        if (this.createdTime != null) {
            this.createdTimeStr = DateUtil.formatDatetime(this.createdTime);
        }
        return createdTimeStr;
    }

    public String getUpdatedTimeStr() {
        if (this.updatedTime != null) {
            this.updatedTimeStr = DateUtil.formatDatetime(this.updatedTime);
        }
        return updatedTimeStr;
    }
}
