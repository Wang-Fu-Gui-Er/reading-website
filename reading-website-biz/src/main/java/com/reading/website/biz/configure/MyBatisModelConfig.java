package com.reading.website.biz.configure;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 配置MyBatis,引入数据源，sqlSessionFactory,sqlSessionTemplate,事务管理器
 *
 * @yx8102   2019/1/7
 */
@Configuration  //配置类
@EnableTransactionManagement //允许使用事务管理器
public class MyBatisModelConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource); //设置数据源
        ssfb.setTypeAliasesPackage("com.reading.website.api.domain");   //设置扫描模型包【po】
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mapper/*.xml");
            ssfb.setMapperLocations(resources);
            return ssfb.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Bean   //获得Session 模板，从而获得Session
    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override   //事务管理器
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
