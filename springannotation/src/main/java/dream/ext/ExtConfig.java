package dream.ext;

import dream.beans.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 拓展原理
 *      BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
 *      BeanFactoryPostProcessor：beanFactory的后置处理器：
 *          在BeanFactory标准初始化之后调用，所有bean的定义已经保存加载到beanFactory，但是bean的实例还未创建
 *
 *      1.IOC容器创建对象
 *      2.invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors
 *          如何找到所有的BeanFactoryPostProcessors并执行他们的方法
 *              1）.直接在BeanFactory中找到所有类型是BeanFactoryPostProcessors的组件并执行他们的方法
 *              2）.在初始化创建其他组件之前进行
 */


@Configuration
@ComponentScan("dream.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }

}
