package com.guming.orderTemplate.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guming.common.base.vo.BaseVo;
import com.guming.common.base.vo.MapVo;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/20
 */
@Data
public class TemplatesVo extends BaseVo {

    @Setter
    @Getter
    @ApiModelProperty(value = "模板id")
    private Long id;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板名称")
    private String name;

    @Setter
    @Getter
    @JsonIgnore
    private Date createdTime;

    @Setter
    @Getter
    @JsonIgnore
    private Date updatedTime;

    @Setter
    @ApiModelProperty(value = "更新时间")
    private String updatedTimeStr;

    @Setter
    @ApiModelProperty(value = "创建时间")
    private String createdTimeStr;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板是否有效")
    private Boolean isActive;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板类型")
    private MapVo templatesTypeMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板所属仓库")
    private MapVo tagwareHouseMapVo;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板内商品的参数")
    private List<TemplateProductsVo> templateProductsMapVoList;

    @Setter
    @Getter
    @ApiModelProperty(value = "模板所属路线")
    private List<MapVo> setupsTaglineMapVoList;

    public String getUpdatedTimeStr() {
        if (this.updatedTime != null){
            this.updatedTimeStr = DateUtil.formatDatetime(this.updatedTime);
        }
        return updatedTimeStr;
    }

    public String getCreatedTimeStr() {
        if (this.createdTime != null){
            this.createdTimeStr = DateUtil.formatDatetime(this.createdTime);
        }
        return createdTimeStr;
    }
}
