package dream.condition;

import dream.beans.Yellow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param annotationMetadata       当前类的注解信息
     * @param beanDefinitionRegistry  BeanDefinition注册类  把所有需要添加到容器中的Bean，
     *                                调用beanDefinitionRegistry.registerBeanDefinition手工注册进来
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        boolean red = beanDefinitionRegistry.containsBeanDefinition("dream.beans.Red");
        boolean blue = beanDefinitionRegistry.containsBeanDefinition("dream.beans.Blue");
        if(red && blue){
            //指定Bean定义信息：Bean的类型，etc.
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Yellow.class);
            //注册一个Bean，指定bean
            beanDefinitionRegistry.registerBeanDefinition("yellow",rootBeanDefinition);
        }
    }
}
