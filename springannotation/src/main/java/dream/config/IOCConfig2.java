package dream.config;

import dream.beans.Color;
import dream.beans.ColorFactoryBean;
import dream.beans.Person;
import dream.condition.LinuxCondition;
import dream.condition.MyImportBeanDefinitionRegistrar;
import dream.condition.MyImportSelector;
import org.springframework.context.annotation.*;

@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
public class IOCConfig2 {



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
    //@Lazy
    public Person person() {
        System.out.println("给容器中添加person...");
        return new Person("李四", 50);
    }
    /**
     * @Conditional:按照一定条件判断，符合条件的向容器中注册Bean
     */
    //@Conditional({WinCondition.class})
    @Bean(value = "bill")
    public Person person01() {
        return new Person("bill gates", 65);
    }

    @Conditional({LinuxCondition.class})
    @Bean(value = "linus")
    public Person person02() {
        return new Person("linus", 46);
    }

    /**
     * 给容器中注册组件：
     *      1.包扫描 + 组件标注注解（@Controller/@Service，etc.）
     *      2.@Bean [导入的第三方包里面的组件]
     *      3.@Import[快速给容器导入组件，id默认是组件全类名]：
     *          1）.@Import(xxx.class)，容器就会自动注册该组件
     *          2）.ImportSelector(),返回需要导入的组件的全类名数组
     *          3）.ImportBeanDefinitionRegistrar，手动注册Bean到容器中
     *          4）.使用Spring提供的FactoryBean（工厂Bean）：
     *              a.默认获取的是工厂Bean调用getObject()创建的对象；
     *              b.要获取工厂Bean本身，需要再id前面加一个&：applicationContext.getBean("&colorFactoryBean");
     */

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }


}
