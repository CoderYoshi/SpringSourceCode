import dream.beans.Car;
import dream.config.AutowiredConfig;
import dream.dao.BookDao;
import dream.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOC_AutowiredTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AutowiredConfig.class);

    /**
     * 测试自动注入组件
     */
    @Test
    public void printDaoTest(){

        BookService bookService = annotationConfigApplicationContext.getBean(BookService.class);
        System.out.println(bookService);

        BookDao bookDao = annotationConfigApplicationContext.getBean(BookDao.class);
        System.out.println(bookDao);
    }

    /**
     * 测试自定义组件引入Spring顶层组件
     */
    @Test
    public void awareTest(){
        Car car = annotationConfigApplicationContext.getBean(Car.class);
        System.out.println(annotationConfigApplicationContext);
        car.printApplicationContext();
    }
}
