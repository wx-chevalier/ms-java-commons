package cn.msjc.web.bootstrap.config;

import static com.google.common.base.Preconditions.checkNotNull;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.msjc.analytics.common.data.TenantId;
import com.msjc.analytics.common.data.WorkOrderId;
import com.msjc.infrastructure.mybatis.MyBatisTools;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
public class DatasourceConfig {

  // Primary data source and its mybatis related config

  @Bean
  @Primary
  public DataSourceProperties primaryDataSourceProps() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  public DataSource primaryDataSource() {
    return primaryDataSourceProps()
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean
  @Primary
  SqlSessionFactory primarySqlSessionFactory(DataSource dataSource) throws Exception {
    MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();

    bean.setDataSource(dataSource);

    configPlugins(bean);

    SqlSessionFactory sqlSessionFactory =
        checkNotNull(bean.getObject(), "error creating primarySqlSessionFactory");

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
  }

  // Extra data sources

  @Bean
  @ConfigurationProperties("application.datasource.msjc")
  public DataSourceProperties msjcDataSourceProps() {
    return new DataSourceProperties();
  }

  @Bean(name = "msjcDataSource")
  public DataSource msjcDataSource() {
    return msjcDataSourceProps().initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }
}
