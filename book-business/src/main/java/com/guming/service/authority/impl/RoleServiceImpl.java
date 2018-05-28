package com.guming.service.authority.impl;

import com.guming.authority.dto.RoleAddDto;
import com.guming.authority.dto.RoleUpdateDto;
import com.guming.authority.dto.query.RoleQuery;
import com.guming.authority.entity.Role;
import com.guming.authority.vo.RoleVo;
import com.guming.common.base.dto.IdDto;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.base.vo.TreeVo;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.dao.authority.RoleRepository;
import com.guming.service.authority.AuthorityService;
import com.guming.service.authority.MenuService;
import com.guming.service.authority.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private MenuService menuService;

    @Override
    protected BaseRepository getRepository() {
        return this.roleRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam addRole(RoleAddDto roleAddDto) {
        if (StringUtils.isEmpty(roleAddDto.getRoleName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NAME_EMPTY);
        }

        if (roleRepository.countAllByRoleName(roleAddDto.getRoleName())>=1){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NAME_REPEAT);
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleAddDto,role,"id");
        role.setRoleLevel(2);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        role = roleRepository.save(role);
        authorityService.addRoleAuthority(roleAddDto.getAuthorityDtoList(),role.getId());
        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateRole(RoleUpdateDto roleUpdateDto) {
        if (roleUpdateDto.getId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_ID_EMPTY);
        }
        if (StringUtils.isEmpty(roleUpdateDto.getRoleName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NAME_EMPTY);
        }

        Role role = roleRepository.findOne(roleUpdateDto.getId());
        if (role == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_EXISTS);
        }
        if (!role.getRoleName().equals(roleUpdateDto.getRoleName()) && roleRepository.countAllByRoleName(roleUpdateDto.getRoleName())>=1){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NAME_REPEAT);
        }
        BeanUtils.copyProperties(roleUpdateDto,role);
        role.setUpdateDate(new Date());
        roleRepository.save(role);
        authorityService.updateRoleAuthority(roleUpdateDto.getAuthorityDtoList(),roleUpdateDto.getId());
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam deleteRole(List<Long> ids) {
        if (ids==null || ids.isEmpty()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_ROLE_NOT_SELECT);
        }
        roleRepository.deleteAllByIds(ids);
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<RoleVo>> findRole(RoleQuery roleQuery) {
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(roleQuery.getRoleName())){
                    Predicate likeRoleName = criteriaBuilder.like(root.get("roleName").as(String.class),"%" + roleQuery.getRoleName()+"%");
                    predicates.add(likeRoleName);
                }
                return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        Pageable pageable = new PageRequest(roleQuery.getPage(),roleQuery.getPageSize());
        Page<Role> pageResult=roleRepository.findAll(specification,pageable);
        Pagination pagination = new Pagination(pageResult.getTotalElements(),pageResult.getNumber(),pageResult.getSize());
        List<RoleVo> result = new ArrayList<>();
        List<Role> roleList = pageResult.getContent();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                RoleVo roleVo = new RoleVo();
                BeanUtils.copyProperties(role, roleVo);
                //角色等级为1是系统固有角色，无法删除通知前端
                if (role.getRoleLevel().equals(1)){
                    roleVo.setIsDelete(true);
                }
                result.add(roleVo);
            }
        }
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam edit(IdDto idDto) {
        Role role = roleRepository.findOne(idDto.getId());
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role,roleVo);
        List<TreeVo> treeVoList = menuService.findMenuTreeWithCheckedByRole(idDto.getId());
        roleVo.setTreeVoList(treeVoList);
        return ResponseParam.success(roleVo);
    }

    @Override
    public ResponseParam<List<RoleVo>> findAllRole() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleVo> roleVoList = new ArrayList<RoleVo>();
        for (Role role: roleList){
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role,roleVo);
            roleVoList.add(roleVo);
        }
        return ResponseParam.success(roleVoList);
    }
}
