import dream.config.BeanLifecycleConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleTest {


    @Test
    public void test(){
        //创建IOC容器
        AnnotationConfigApplicationContext lifecycleConfigContext = new AnnotationConfigApplicationContext(BeanLifecycleConfig.class);
        System.out.println("容器创建完成...");
        lifecycleConfigContext.close();
    }
}
