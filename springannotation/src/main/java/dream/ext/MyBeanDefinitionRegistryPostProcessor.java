package dream.ext;

import dream.beans.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {



    //BeanDefinitionRegistry Bean定义信息的保存中心，以后BeanFactory就是按照BeanDefinitionRegistry中保存的每一个定义信息创建bean实例的
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry...bean的数量："+ registry.getBeanDefinitionCount());

        //两种获得beanDefinition的方式
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Blue.class);
        //AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();

        registry.registerBeanDefinition("hello",rootBeanDefinition);

    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor....bean的数量："+ beanFactory.getBeanDefinitionCount());

    }
}
