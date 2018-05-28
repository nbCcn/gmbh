package com.guming.authority.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/16
 */
@ApiModel(value = "UserVo",description = "返回的用户信息实体类")
@Data
public class UserVo {
    @ApiModelProperty(value = "账户id")
    private Long id;

    @ApiModelProperty(value = "账号名")
    private String userName;

    @ApiModelProperty(value = "用户名")
    private String firstName;

    @ApiModelProperty(value = "用户姓氏")
    private String secondName;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "是否冻结")
    private Boolean isActive;

    @ApiModelProperty(value = "是否超级用户")
    private Boolean isSuperuser;

    @JsonIgnore
    private Date lastLoginTime;

    @ApiModelProperty(value = "最后登录时间")
    @Setter
    private String lastLoginTimeStr;

    @JsonIgnore
    private Date joinedTime;

    @ApiModelProperty(value = "加入时间")
    @Setter
    private String joinedTimeStr;

    @ApiModelProperty(value = "对应的角色id")
    private List<Long> roleIds;

    @ApiModelProperty(value = "对应的角色信息")
    private List<RoleVo> roleVoList;

    @ApiModelProperty(value = "用户联系电话")
    private String phone;

    public String getLastLoginTimeStr() {
        if(this.lastLoginTime != null){
            this.lastLoginTimeStr = DateUtil.formatDatetime(this.lastLoginTime);
        }
        return lastLoginTimeStr;
    }
    public String getJoinedTimeStr() {
        if(this.joinedTime != null){
            this.joinedTimeStr = DateUtil.formatDatetime(this.joinedTime);
        }
        return joinedTimeStr;
    }

}
