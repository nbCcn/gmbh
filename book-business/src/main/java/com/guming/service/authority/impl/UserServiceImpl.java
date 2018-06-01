package com.guming.service.authority.impl;

import com.guming.authority.dto.ChangePassDto;
import com.guming.authority.dto.UserAddDto;
import com.guming.authority.dto.UserUpdateDto;
import com.guming.authority.dto.query.UserQuery;
import com.guming.authority.entity.Role;
import com.guming.authority.entity.User;
import com.guming.authority.vo.RoleVo;
import com.guming.authority.vo.UserVo;
import com.guming.common.base.repository.BaseRepository;
import com.guming.common.base.service.BaseServiceImpl;
import com.guming.common.base.vo.Pagination;
import com.guming.common.base.vo.ResponseParam;
import com.guming.common.constants.ErrorMsgConstants;
import com.guming.common.constants.RegexConstants;
import com.guming.common.exceptions.ErrorMsgException;
import com.guming.common.utils.PBKDF2PasswordHasher;
import com.guming.common.utils.RandomStringUtil;
import com.guming.config.BookConfig;
import com.guming.dao.authority.RoleRepository;
import com.guming.dao.authority.UserRepository;
import com.guming.service.authority.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/15
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected BaseRepository getRepository() {
        return this.userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam updateUser(UserUpdateDto userUpdateDto) {
        updateUserMsg(userUpdateDto);
        return getSuccessUpdateResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUserMsg(UserUpdateDto userUpdateDto){
        if (userUpdateDto.getId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ID_EMPTY);
        }
        if (StringUtils.isEmpty(userUpdateDto.getUserName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_LOGIN_USERNAME_EMPTY);
        }
        User user = userRepository.findOne(userUpdateDto.getId());
        if (user == null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_EXISTS);
        }
        if (!userUpdateDto.getUserName().equals(user.getUserName())){
            Long count = userRepository.countByUserName(userUpdateDto.getUserName());
            if (count != null && count>=1){
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NAME_EXISTS);
            }
        }
        BeanUtils.copyProperties(userUpdateDto,user,"userPass","id");
        user.setUpdateTime(new Date());

        userRoleHandler(user,userUpdateDto.getRoleIds(),userUpdateDto.getIsSuperuser());
        return userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam addUser(UserAddDto userAddDto) {
        addUserMsg(userAddDto);
        return getSuccessAddResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addUserMsg(UserAddDto userAddDto){
        //添加用户时无需再同步添加密码，默认取初始密码
        if (StringUtils.isEmpty(userAddDto.getUserName())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NAME_PASS_EMPTY);
        }
        Long count = userRepository.countByUserName(userAddDto.getUserName());
        if (count != null && count>=1){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NAME_EXISTS);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddDto,user);
        String initPass = RandomStringUtil.generateRandomString(12);
        user.setInitPass(initPass);
        user.setUserPass(new PBKDF2PasswordHasher().encode(initPass));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        userRoleHandler(user,userAddDto.getRoleIds(),userAddDto.getIsSuperuser());
        return userRepository.save(user);
    }


    private void userRoleHandler(User user, List<Long> roleIds,Boolean isSuperUser){
        //超级用户无需配置角色
        if (!isSuperUser) {
            if (roleIds == null || roleIds.isEmpty()) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ROLE_NOT_EXISTS);
            }
            List<Role> roles = roleRepository.findRolesByIds(roleIds);
            if (roles == null || roles.size() != roleIds.size()) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ROLE_NOT_EXISTS);
            }
            user.setRoleList(roles);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam deleteUser(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_SELECT);
        }
        List<User> users =  userRepository.findAll(ids);

        List<User> deleteUserList = new ArrayList<>();
        String unDeleteUser = "";
        if (users!=null && !users.isEmpty()){
            for(User user: users){
                Integer userShopSize = user.getShopsShops().size();
                if (userShopSize >0){
                    unDeleteUser += user.getUserName()+",";
                }else{
                    deleteUserList.add(user);
                }
            }
            if (deleteUserList!=null && !deleteUserList.isEmpty()) {
                userRepository.delete(deleteUserList);
            }
            if (!StringUtils.isEmpty(unDeleteUser)){
                unDeleteUser = unDeleteUser.substring(0,unDeleteUser.length()-1);
                return ResponseParam.error(ErrorMsgConstants.OPTION_FAILED_HELF_CODE,i18nHandler(ErrorMsgConstants.ERROR_VALIDATION_USER_SHOP_EXISTS,unDeleteUser));
            }
        }
        return getSuccessDeleteResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<List<UserVo>> findUserByPage(UserQuery userQuery) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(userQuery.getUserName())){
                    predicates.add(
                            criteriaBuilder.or(
                                    criteriaBuilder.like(root.get("userName").as(String.class),"%" + userQuery.getUserName()+"%"),
                                    criteriaBuilder.like(root.get("firstName").as(String.class),"%" + userQuery.getUserName()+"%")
                            )
                    );
                }
                if (userQuery.getRoleIdList()!=null && !userQuery.getRoleIdList().isEmpty()){
                    CriteriaBuilder.In in = criteriaBuilder.in(root.join("roleList",JoinType.LEFT).get("id").as(Long.class));
                    for (Long roleId : userQuery.getRoleIdList()){
                        in.value(roleId);
                    }
                    predicates.add(in);
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Pageable pageable = new PageRequest(userQuery.getPage(),userQuery.getPageSize());
        Page<User> userPage=userRepository.findAll(specification,pageable);
        Pagination pagination = new Pagination(userPage.getTotalElements(),userPage.getNumber(),userPage.getSize());
        List<UserVo> result = new ArrayList<UserVo>();
        List<User> userList = userPage.getContent();
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user, userVo);
                List<RoleVo> roleVoList = new ArrayList<RoleVo>();
                List<Role> roleList =  user.getRoleList();
                if (roleList!=null && !roleList.isEmpty()){
                    for (Role role: roleList){
                        RoleVo roleVo = new RoleVo();
                        BeanUtils.copyProperties(role,roleVo);
                        roleVoList.add(roleVo);
                    }
                    userVo.setRoleVoList(roleVoList);
                }
                result.add(userVo);
            }
        }
        return ResponseParam.success(result,pagination);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam<UserVo> findById(Long id) {
        if (id ==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ID_EMPTY);
        }
        User user = userRepository.findOne(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        List<Role> roleList = user.getRoleList();
        List<Long> roleIds = new ArrayList<Long>();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                roleIds.add(role.getId());
            }
        }
        userVo.setRoleIds(roleIds);
        return ResponseParam.success(userVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseParam changePass(ChangePassDto changePassDto) {
        if (changePassDto.getId()==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_ID_EMPTY);
        }
        if (!changePassDto.getNewPass().equals(changePassDto.getValidatePass())){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS_DIFFER);
        }
        //密码强度验证
        Matcher matcher = Pattern.compile(RegexConstants.PASSWORD).matcher(changePassDto.getNewPass());
        if (!matcher.matches()){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS_FORMAT_ERROR);
        }

        User user = userRepository.findUserById(changePassDto.getId());
        if (user ==null){
            throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_NOT_EXISTS);
        }
        if (!StringUtils.isEmpty(user.getUserPass())) {
            if (!new PBKDF2PasswordHasher().checkPassword(changePassDto.getOldPass(), user.getUserPass())) {
                throw new ErrorMsgException(ErrorMsgConstants.ERROR_VALIDATION_USER_PASS);
            }
            if (changePassDto.getNewPass().equals(changePassDto.getOldPass())) {
                return getSuccessUpdateResult();
            }
        }

        user.setUserPass(new PBKDF2PasswordHasher().encode(changePassDto.getNewPass()));
        user.setInitPass(null);
        user.setUpdateTime(new Date());
        userRepository.save(user);
        return getSuccessUpdateResult();
    }

}
