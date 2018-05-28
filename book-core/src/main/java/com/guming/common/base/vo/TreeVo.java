package com.guming.common.base.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

/**
 * @Author: PengCheng
 * @Description: 树模型
 * @Date: 2018/4/12
 */
@Api(value = "树信息结构")
public class TreeVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "当前节点名")
    private String name;

    @ApiModelProperty(value = "父节点id")
    private String pid;

    @ApiModelProperty(value = "路径")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序码")
    private Integer order;

    @ApiModelProperty(value = "是否开启")
    private Boolean checked = false;

    @ApiModelProperty(value = "是否菜单",notes = "暂时无用")
    private Boolean isMenu=false;

    @ApiModelProperty(value = "包含的操作权限组",notes = "add、edit、delete、look、import、export")
    private Set<PermissionVo> permissions;

    @ApiModelProperty(value = "拥有的操作权限组",notes = "add、edit、delete、look、import、export")
    private Set<String> ownedPermissions;

    @ApiModelProperty(value = "子节点数据")
    private List<TreeVo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getMenu() {
        return isMenu;
    }

    public void setMenu(Boolean menu) {
        isMenu = menu;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    public Set<PermissionVo> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionVo> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getOwnedPermissions() {
        return ownedPermissions;
    }

    public void setOwnedPermissions(Set<String> ownedPermissions) {
        this.ownedPermissions = ownedPermissions;
    }
}
