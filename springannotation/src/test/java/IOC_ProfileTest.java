import dream.config.ProfileConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOC_ProfileTest {

    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ProfileConfig.class);


    /**
     *     1.使用命令行动态参数：在虚拟机参数位置加载 -Dspring.profiles.active=test
     *     2.用代码形式设置环境
     */
    @Test
    public void printBeansTest(){
        //1.创建一个IOC容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //2.设置运行环境
        context.getEnvironment().addActiveProfile("dev");//可以设置多个环境
        //3.注册主配置类
        context.register(ProfileConfig.class);
        //4.启动刷新容器
        context.refresh();

        String[] beanDefinitionNames = context.getBeanNamesForType(DataSource.class);
        for (String name: beanDefinitionNames
             ) {
            System.out.println(name);
        }
    }

    @Test
    public void setEnvironmentTest(){
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanNamesForType(DataSource.class);
        for (String name: beanDefinitionNames
        ) {
            System.out.println(name);
        }
    }
}
