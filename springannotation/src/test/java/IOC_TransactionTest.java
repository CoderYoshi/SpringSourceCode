import dream.transaction.TransactionConfig;
import dream.transaction.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOC_TransactionTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(TransactionConfig.class);

    @Test
    public void jdbcTest(){

        UserService userServe = annotationConfigApplicationContext.getBean(UserService.class);
        userServe.insertUser();

    }


}
