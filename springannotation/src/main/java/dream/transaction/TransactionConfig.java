package dream.transaction;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *  声明式事务
 *
 *  环境搭建
 *      1.导入相关依赖
 *          数据源、数据库驱动、Spring-JDBC模块
 *      2.配置数据源JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
 *
 *
 *
 */
@ComponentScan("dream.transaction")
@Configuration
public class TransactionConfig {


    //数据源
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("dbuser");
        dataSource.setPassword("10121416");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.24:3306/lm_im2?characterEncoding=UTF-8");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }



    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {

        //Spring对@Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

        return jdbcTemplate;
    }

}
