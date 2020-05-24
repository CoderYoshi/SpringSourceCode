import dream.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExtTest {

    @Test
    public void test(){



        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);





        configApplicationContext.close();
    }


}
