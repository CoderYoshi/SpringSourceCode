package dream.config;

import dream.beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig2 {



    /**
     * prototype：多实例  IOC容器启动时不会调用方法，每次获取时才会调用方法创建对象
     * singleton：单实例  IOC容器启动会调用方法创建对象放到容器中，以后每次获取就是直接从容器中拿
     * request：同一次请求创建一个实例
     * session：同一个session创建一个实例
     *
     * @return
     * 默认是单实例Bean:默认在容器启动时创建对象
     *
     * 懒加载：容器启动不创建对象，第一次获取时创建对象并初始化
     *
     *
     */
    @Bean(value = "person")
    //懒加载注解
    @Lazy
    public Person person() {
        System.out.println("给容器中添加person...");
        return new Person("李四", 50);
    }
}
