import dream.beans.Person;
import dream.config.MainConfig;
import dream.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class MainTest {

    //通过xml文件向容器中注册一个Bean
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
    }


    @Test
    //通过注解方式向容器注入一个Bean
    public void javaConfigTest(){
        ApplicationContext applicationContext =new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        //根据指定类型查询Bean的名字
        String[] beanNames = applicationContext.getBeanNamesForType(Person.class);
        for (String name: beanNames
             ) {
            System.out.println(name + "-----------------------------------");
        }
        //查询容器中组件的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
             ) {
            System.out.println(name);
        }

    }

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
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
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
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("IOC容器创建成功...");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }

        Object person = applicationContext.getBean("person");
        Object person1 = applicationContext.getBean("person");

        System.out.println(person==person1);

    }

    /**
     * 懒加载测试
     */
    @Test
    public void LazyLodeTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("IOC容器创建成功...");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }

        Object person = applicationContext.getBean("person");
        Object person1 = applicationContext.getBean("person");

        System.out.println(person==person1);
    }

    /**
     * 条件注册
     */
    @Test
    public void ConditionalTest(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        Environment environment = applicationContext.getEnvironment();  //获取IOC容器的运行环境
        String property = environment.getProperty("os.name");
        System.out.println(property);
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Person.class);
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }
    }
}
