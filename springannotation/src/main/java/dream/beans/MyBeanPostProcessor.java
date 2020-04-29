package dream.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器：初始化前后进行工作
 */
//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {

        System.out.println("postProcessBeforeInitialization..."+s+"===>"+o);
        return o;
    }


    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        System.out.println("postProcessAfterInitialization..."+s+"===>"+o);
        return o;
    }
/**
 * spring底层对BeanPostProcessor的使用：
 *      实现 bean赋值、注入其他组件、@Autowired、生命周期注解功能、@Async，.etc  功能
 */

}
