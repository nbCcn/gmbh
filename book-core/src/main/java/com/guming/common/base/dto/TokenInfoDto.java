package com.guming.common.base.dto;

/**
 * @Author: PengCheng
 * @Description: 带token的用户登陆信息对象
 * @Date: 2018/4/12
 */
public class TokenInfoDto extends BaseDto {

    private String token;

    private String userName;

    private String userPass;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
