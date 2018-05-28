package com.guming.authority.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guming.common.base.dto.BaseDto;
import com.guming.common.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/21
 */
@ApiModel(value = "UserAddDto",description = "用户添加用实体类")
@Data
public class UserAddDto extends BaseDto {

    @ApiModelProperty(value = "账号名",required = true)
    private String userName;

    @ApiModelProperty(value = "用户名",required = true)
    private String firstName;

    @ApiModelProperty(value = "用户姓氏",notes = "暂未使用")
    private String secondName;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "是否冻结",required = true)
    private Boolean isActive = true;

    @ApiModelProperty(value = "是否超级用户",required = true)
    private Boolean isSuperuser;

    @ApiModelProperty(value = "加入时间")
    private String joinedTimeStr;

    @JsonIgnore
    @Setter
    private Date joinedTime;

    @ApiModelProperty(value = "对应的角色id",required = true)
    private List<Long> roleIds;

    @ApiModelProperty(value = "用户电话",required = true)
    private String phone;

    public Date getJoinedTime() {
        if (!StringUtils.isEmpty(this.joinedTimeStr)){
            this.joinedTime =DateUtil.parseDatetime(this.joinedTimeStr);
        }
        return this.joinedTime;
    }
}
