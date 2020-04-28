package dream.config;

import dream.beans.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * bean的生命周期：创建---初始化---销毁，交由容器管理
 * 可以自定义初始化和销毁方法，容器在进行到当期生命周期时可以调用自定义的方法
 *      1.指定初始化和销毁方法:
 *          （xml配置：指定init-method和destroy-method)
 *          通过@Bean指定init-method和destroy-method
 *      初始化：对象创建完成并赋值好，调用init()方法进行初始化；
 *      销毁：  单实例：容器关闭的时候调用destroy()方法
 *              多实例：容器不会处理，需要手动调用destroy()方法
 *      2.通过让Bean实现InitializingBean接口（定义初始化逻辑），
 *                      DisposableBean接口（定义销毁逻辑）
 *      3.可以使用JSR250：
 *          @PostConstruct：在Bean创建完成并且属性赋值完成，执行初始化方法
 *          @preDestroy：在容器销毁Bean之前，通知我们进行清理工作
 *      4.BeanPostProcessor接口:bean的后置处理器，在Bean初始化前后进行一些工作
 *              postProcessBeforeInitialization：在初始化之前工作
 *              postProcessAfterInitialization：在初始化之后工作
 *
 */
@ComponentScan("dream.beans")
@Configuration
public class BeanLifecycleConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car getCar(){
        return new Car();

    }


}
