package com.guming.authority.dto.query;

import com.guming.common.base.dto.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "UserQuery",description = "用户查询参数实体")
@Data
public class UserQuery extends PageQuery {

    @ApiModelProperty(value = "账户名or用户名")
    private String userName;

    @ApiModelProperty(value = "角色id")
    private List<Long> roleIdList;

}
