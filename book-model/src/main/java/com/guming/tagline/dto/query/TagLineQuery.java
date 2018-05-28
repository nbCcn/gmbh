package com.guming.tagline.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/4/20 09:54
 */
@Data
@ApiModel(value = "TagLineQuery", description = "送货路线查询实体")
public class TagLineQuery extends PageQuery {

    @ApiModelProperty(value = "仓库Id")
    private Long tagwareHouseId;


}
