import dream.transaction.TransactionConfig;
import dream.transaction.UserServe;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOC_TransactionTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TransactionConfig.class);

    @Test
    public void jdbcTest(){

        UserServe userServe = annotationConfigApplicationContext.getBean(UserServe.class);
        userServe.insertUser();
    }


}
