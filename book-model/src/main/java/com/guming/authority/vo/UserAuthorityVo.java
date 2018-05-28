package com.guming.authority.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
@Data
public class UserAuthorityVo {
    private Long id;
    private String userName;
    private String userPass;
    private String tokenPassInfo;
    private String firstName;
    private String secondName;
    private String email;
    private Boolean isSuperuser;
    private List<RoleAuthorityVo> roleAuthorityDtoList;
}
