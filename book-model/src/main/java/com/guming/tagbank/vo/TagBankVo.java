package com.guming.tagbank.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Ccn
 * @Description:
 * @Date: 2018/6/5 14:25
 */

@ApiModel(value = "TagBankVo", description = "返回的银行账户信息实体")
public @Data
class TagBankVo {

    @ApiModelProperty(value = "Id")
    private Long id;
    @ApiModelProperty(value = "银行账号")
    private String num;
    @ApiModelProperty(value = "银行名称")
    private String name;
    @ApiModelProperty(value = "支行名称")
    private String branch;
    @ApiModelProperty(value = "收款人")
    private String owner;

}
