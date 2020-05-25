import dream.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExtTest {

    @Test
    public void test(){



        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        //发布事件
        configApplicationContext.publishEvent(new ApplicationEvent(new String("我发布了事件")) {

        });



        configApplicationContext.close();
    }


}
