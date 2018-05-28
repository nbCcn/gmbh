package com.guming.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: PengCheng
 * @Description:
 * @Date: 2018/5/11
 */
/*@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactoryKingdee",
        transactionManagerRef="transactionManagerKingdee",
        basePackages= { "com.gumingnc.book.kingdee" }) //设置Repository所在位置*/
public class KingdeeJpaConfig {

    @Autowired
    @Qualifier("kingdeeDataSource")
    private DataSource kingdeeDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "entityManagerKingdee")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryKingdee(builder).getObject().createEntityManager();
    }
    @Bean(name = "entityManagerFactoryKingdee")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryKingdee (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(kingdeeDataSource)
                .properties(getVendorProperties(kingdeeDataSource))
                .packages("com.gumingnc.book.kingdee") //设置实体类所在位置
                .persistenceUnit("kingdeePersistenceUnit")
                .build();
    }


    private Map getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }
    @Bean(name = "transactionManagerKingdee")
    PlatformTransactionManager transactionManagerKingdee(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryKingdee(builder).getObject());
    }
}
