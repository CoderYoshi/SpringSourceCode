import dream.beans.Person;
import dream.config.PropertyValueConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOC_PropertyValueTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(PropertyValueConfig.class);

    @Test
    public void printBeansTest(){
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames
             ) {
            System.out.println(name);
        }
    }


    @Test
    public void printPerson(){
        Person person = (Person) annotationConfigApplicationContext.getBean("person");
        System.out.println(person);

    }
}
