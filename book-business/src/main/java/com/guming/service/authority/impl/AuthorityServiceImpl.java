package com.guming.service.authority.impl;

import com.guming.authority.dto.AuthorityDto;
import com.guming.authority.entity.RoleMenu;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.constants.business.OperationType;
import com.guming.dao.authority.AuthorityRepository;
import com.guming.service.authority.AuthorityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/12
 */
@Service
public class AuthorityServiceImpl extends BaseServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.authorityRepository;
    }

    @Override
    public List<RoleMenu> findRoleOperationAuthority(String menuCode, Long roleId){
       return authorityRepository.findRoleMenuByRoleIdAndMenuCode(roleId,menuCode);
    }

    @Override
    public List<RoleMenu> findRoleAuthority(Long roleId){
        return authorityRepository.findRoleMenuByRoleId(roleId);
    }

    @Override
    @Transactional
    public void addRoleAuthority(List<AuthorityDto> authorityDtoList, Long roleId){
        List<RoleMenu> roleMenuList = new ArrayList<>();
        if (authorityDtoList!= null && !authorityDtoList.isEmpty()) {
            for (AuthorityDto authorityDto : authorityDtoList) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                BeanUtils.copyProperties(authorityDto, roleMenu);
                roleMenu.setMenuOperation(OperationType.getType(authorityDto.getOperation()));
                roleMenuList.add(roleMenu);
            }
            authorityRepository.save(roleMenuList);
        }
    }

    @Override
    @Transactional
    public void updateRoleAuthority(List<AuthorityDto> authorityDtoList, Long roleId){
        authorityRepository.deleteAllByRoleId(roleId);
        addRoleAuthority(authorityDtoList,roleId);
    }
}
