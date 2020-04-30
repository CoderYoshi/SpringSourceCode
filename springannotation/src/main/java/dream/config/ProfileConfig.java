package dream.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import dream.beans.Yellow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *  Profile:
 *      Spring提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 * @Profile:指定组件在哪个环境下才能注册到容器中
 *      1.加了环境标识的bean，只有这个环境激活时才会被注册到容器中。默认是开启default环境
 *      2.写在配置类上，只有在指定环境下，配置类才会被加载
 *      3.没有标注的在任何环境下都可以被加载
 *
 */

@Configuration
public class ProfileConfig {

    @Profile("test")
    @Bean
    public Yellow yellow(){
        return new Yellow();
    }

    @Profile("test")
    @Bean
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser("dbuser");
        comboPooledDataSource.setPassword("10121416");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://192.168.0.24:3306/aihome_test?characterEncoding=UTF-8");
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        return comboPooledDataSource;
    }


    @Profile("dev")
    @Bean
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser("dbuser");
        comboPooledDataSource.setPassword("10121416");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://192.168.0.24:3306/aihome_dev?characterEncoding=UTF-8");
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        return comboPooledDataSource;
    }


    @Profile("prod")
    @Bean
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser("dbuser");
        comboPooledDataSource.setPassword("10121416");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://192.168.0.24:3306/aihome?characterEncoding=UTF-8");
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        return comboPooledDataSource;
    }
}
