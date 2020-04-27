import dream.beans.Person;
import dream.config.IOCConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext applicationContext =new AnnotationConfigApplicationContext(IOCConfig.class);
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


}
