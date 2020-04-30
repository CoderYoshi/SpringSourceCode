import dream.AOP.MathCalculator;
import dream.config.AOPConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

    @Test
    public void test(){
        //创建IOC容器
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(AOPConfig.class);
        //1.不要自己创建对象，要使用IOC容器中的对象
        MathCalculator mathCalculator = configApplicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1,1);
        configApplicationContext.close();
    }


}
