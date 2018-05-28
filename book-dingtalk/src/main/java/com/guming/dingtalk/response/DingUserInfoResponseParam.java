package com.guming.dingtalk.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/7
 */
@Data
public class DingUserInfoResponseParam extends DingTalkResponseParam{

    //在当前isv全局范围内唯一标识一个用户的身份,用户无法修改
    private String unionid;

    //在本服务窗运营服务商范围内,唯一标识关注者身份的id（不可修改）
    private String openId;

    //员工唯一标识ID
    private String userid;

    //在对应的部门中是否为主管, Map结构的json字符串, key是部门的Id, value是人员在这个部门中是否为主管, true表示是, false表示不是
    private String isLeaderInDepts;

    //是否为企业的老板, true表示是, false表示不是（【设置负责人】：主管理员登陆钉钉手机客户端 -【通讯录】-【企业名后面的管理】-【企业通讯录】-【负责人设置】进行添加则可。）
    private Boolean isBoss;

    private Boolean isSenior;

    //分机号（仅限企业内部开发调用）
    private String tel;

    //成员所属部门id列表
    private List<Integer> department;

    //办公地点（ISV不可见）
    private String workPlace;

    //员工的电子邮箱（ISV不可见）
    private String email;

    //在对应的部门中的排序, Map结构的json字符串, key是部门的Id, value是人员在这个部门的排序值
    private String orderInDepts;

    //钉钉Id,在钉钉全局范围内标识用户的身份（不可修改）
    private String dingId;

    //手机号码（ISV不可见）
    private String mobile;

    //是否已经激活, true表示已激活, false表示未激活
    private Boolean active;

    //头像url
    private String avatar;

    //是否为企业的管理员, true表示是, false表示不是
    private Boolean isAdmin;

    //是否号码隐藏, true表示隐藏, false表示不隐藏
    private Boolean isHide;

    //员工工号
    private String jobnumber;

    //成员名称
    private String name;

    private String stateCode;

    //职位信息
    private String position;

}
