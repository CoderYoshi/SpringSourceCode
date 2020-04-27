import dream.beans.Person;
import dream.config.IOCConfig;
import dream.config.IOCConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class IOCTest {
    /**
     * 测试扫描规则
     * includeFilters = Filter{} 指定扫描包的时候按照规则包含哪些组件
     * excludeFilters = Filter{} 指定扫描包的时候按照规则排除哪些组件
     * FilterType.ANNOTATION     按照注解
     * FilterType.ASSIGNABLE_TYPE  按照给定类型
     * FilterType.  ASPECTJ,       使用ASPECTJ表达式
     * FilterType.  REGEX,         使用正则表达式
     * FilterType.  CUSTOM;        自定义规则
     */
    @Test
    //扫描时排除（只包含）某些包
    public void ComponentScanFilterTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IOCConfig.class);
        //查询容器中组件的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }

    }


    /**
     * 测试组件的作用域
     */
    @Test
    public void MainConfig2Test(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IOCConfig2.class);
        System.out.println("IOC容器创建成功...");
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String name: beanDefinitionNames
//        ) {
//            System.out.println(name);
//        }

        Object person = applicationContext.getBean("person");
        Object person1 = applicationContext.getBean("person");

        System.out.println(person==person1);

    }

    /**
     * 懒加载测试
     */
    @Test
    public void LazyLoadTest(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("dream.config");
        System.out.println("IOC容器创建成功...");
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String name: beanDefinitionNames
//        ) {
//            System.out.println(name);
//        }

        Object person = applicationContext.getBean("person");
        Object person1 = applicationContext.getBean("person");

        System.out.println(person==person1);
        applicationContext.close();


    }

    /**
     * 条件注册
     */
    @Test
    public void ConditionalTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IOCConfig2.class);
        Environment environment = applicationContext.getEnvironment();  //获取IOC容器的运行环境
        String property = environment.getProperty("os.name");
        System.out.println(property);
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Person.class);
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }
    }

    /**
     * 测试@Import注解
     */
    @Test
    public void ImportTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(IOCConfig2.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //工厂Bean获取的是调用getObject创建的对象
        Object colorFactoryBean = applicationContext.getBean("colorFactoryBean");
        Object realColorFactoryBean = applicationContext.getBean("&colorFactoryBean");

        System.out.println("bean的类型："+colorFactoryBean.getClass());
        System.out.println("realColorFactoryBean："+realColorFactoryBean);
    }
}
