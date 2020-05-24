package dream.transaction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *  声明式事务
 *
 *  环境搭建
 *      1.导入相关依赖
 *          数据源、数据库驱动、Spring-JDBC模块
 *      2.配置数据源JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
 *      3.给方法上标注@Transactional表示当前方法是一个事务方法
 *      4.@EnableTransactionManagement开启基于注解的事务管理功能
 *      5.配置事务管理器来控制事务
 *
 *  原理：
 *      1.@EnableTransactionManagement
 *          利用TransactionManagementConfigurationSelector向容器中导入组件
 *          导入两个组件：AutoProxyRegistrar   ProxyTransactionManagementConfiguration
 *
 *      2.AutoProxyRegistrar：给容器中注册一个 InfrastructureAdvisorAutoProxyCreator组件
 *          InfrastructureAdvisorAutoProxyCreator组件：利用后置处理器机制在对象创建以后包装对象，
 *          然后返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *
 *
 *      3.ProxyTransactionManagementConfiguration
 *          1).给容器中注册事务增强器：
 *              a.要使用事务注解的信息 AnnotationTransactionAttributeSource解析事务注解
 *              b.事务拦截器：transactionInterceptor 保存了事务属性信息，事务管理器：
 *              是一个MethodInterceptor：在目标方法执行的时候执行拦截器链
 *                  功能：
 *                         Ⅰ.先获取事务相关的属性；
 *                         Ⅱ.再获取 PlatformTransactionManager 平台事务管理器：如果事先没有添加指定任何 TransactionManager，
 *                            最终会从容器中按照类型获取PlatformTransactionManager
 *                         Ⅲ.执行目标方法，如果异常则获取到事务管理器，利用管理器回滚这次操作
 *                                          如果正常，利用事务管理器，提交事务
 *
 *
 *
 *
 */
@ComponentScan("dream.transaction")
@Configuration
@EnableTransactionManagement
public class TransactionConfig {


    //数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("521726");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/transaction_test?characterEncoding=UTF-8");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }



    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {

        //Spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

        return jdbcTemplate;
    }

    //注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

}
