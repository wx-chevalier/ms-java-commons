package com.unionfab.cloud.analytics.stat.mes.infrastructure;

import static com.google.common.base.Preconditions.checkNotNull;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.unionfab.analytics.common.data.TenantId;
import com.unionfab.analytics.common.data.WorkOrderId;
import com.unionfab.infrastructure.mybatis.MyBatisTools;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
    basePackages = "com.unionfab.cloud.analytics.stat.mes.infrastructure.dmr",
    sqlSessionFactoryRef = "ufcSqlSessionFactory")
public class UfcDbConfig {

  @Bean(name = "ufcSqlSessionFactory")
  SqlSessionFactory ufcSqlSessionFactory(@Qualifier("ufcDataSource") DataSource dataSource)
      throws Exception {
    MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();

    bean.setDataSource(dataSource);

    configPlugins(bean);

    SqlSessionFactory sqlSessionFactory =
        checkNotNull(bean.getObject(), "error creating ufcSqlSessionFactory");

    configTypeHandlers(sqlSessionFactory);

    return sqlSessionFactory;
  }

  private void configPlugins(MybatisSqlSessionFactoryBean factoryBean) {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setLimit(100);
    factoryBean.setPlugins(paginationInterceptor);
  }

  private void configTypeHandlers(SqlSessionFactory sqlSessionFactory) {
    TypeHandlerRegistry typeHandlerRegistry =
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();

    MyBatisTools.registerJacksonTypeHandlers(typeHandlerRegistry);
    MyBatisTools.registerBinAndUUIDTypeHandler(typeHandlerRegistry);

    // Entity IDs
    MyBatisTools.registerLongToEntityIdTypeHandler(typeHandlerRegistry, WorkOrderId.class);
    MyBatisTools.registerLongToEntityIdTypeHandler(typeHandlerRegistry, TenantId.class);
  }
}
