package dream.config;

import dream.beans.Person;
import dream.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

//配置类 == 配置文件
@Configuration  //告诉spring这是一个配置类
@ComponentScan(value = "dream", includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyFilterType.class})
}, useDefaultFilters = false)
// @ComponentScan value：指定扫描的包,可以多次使用指定扫描规则
// includeFilters = Filter{} 指定扫描包的时候按照规则包含哪些组件
// excludeFilters = Filter{} 指定扫描包的时候按照规则排除哪些组件
// FilterType.ANNOTATION     按照注解
// FilterType.ASSIGNABLE_TYPE  按照给定类型
// FilterType.  ASPECTJ,       使用ASPECTJ表达式
// FilterType.  REGEX,         使用正则表达式
// FilterType.  CUSTOM;        自定义规则

public class IOCConfig {

    //给容器中注册一个Bean；类型为返回值的类型，默认id值为方法名
    @Bean(value = "person1")  //value 指定Bean的id
    public Person person() {
        return new Person("李四", 20);
    }

}
