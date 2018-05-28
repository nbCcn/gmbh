package com.guming.common.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/4/17
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>  extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {
}
