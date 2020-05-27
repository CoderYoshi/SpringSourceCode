package dream.ext;

import dream.beans.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 拓展原理
 *      BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
 *      1.BeanFactoryPostProcessor：beanFactory的后置处理器：
 *          在BeanFactory标准初始化之后调用，所有bean的定义已经保存加载到beanFactory，但是bean的实例还未创建
 *          BeanFactoryPostProcessor原理：
 *          1）.IOC容器创建对象
 *          2）.invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors
 *           如何找到所有的BeanFactoryPostProcessors并执行他们的方法
 *              a.直接在BeanFactory中找到所有类型是BeanFactoryPostProcessors的组件并执行他们的方法
 *              b.在初始化创建其他组件之前进行
 *       2.BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *          postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)：
 *              在所有bean定义信息将要被加载，bean实例还未创建时；
 *
 *              优先于BeanFactoryPostProcessor执行；利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件
 *          原理：
 *              1）.IOC容器创建对象
 *              2）.refresh() --> invokeBeanFactoryPostProcessors(beanFactory);
 *              3）.从容器中获取到所有的BeanDefinitionRegistryPostProcessors组件
 *                  a.依次触发所有的postProcessBeanDefinitionRegistry()方法
 *                  b.再来触发postProcessBeanFactory()方法 BeanFactoryPostProcessor
 *              4）.再来从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessBeanFactory()方法
 *
 *
 *              invokeBeanFactoryPostProcessors()方法的执行过程：
 *
 *                  1）.首先会将使用者传递进来的BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor实现类拿到，并
 *              分别放到两个list中，regularPostProcessors存放着实现了BeanFactoryPostProcessor的，registryProcessors存放着
 *              实现了BeanDefinitionRegistryPostProcessor的。注意这里的使用者自定义的处理器并不是通过注解方式注入的，
 *              而是通过api方法注入的。是通过AnnotationConfigApplicationContext.addBeanFactoryPostProcessor()方法注入的。
 *              同时在找到BeanDefinitionRegistryPostProcessor的实现类会直接回调postProcessBeanDefinitionRegistry方法。
 *                  2）.接着会将spring内部已经注入的BeanDefinitionRegistryPostProcessor的实现类找出来，这里其实spring内部
 *              只是注入了一个实现类，不过相当重要，这个对象就是是ConfigurationClassPostProcessor，它完成了spring的许多工作，
 *              这里将它拿出来，放到一个currentRegistryProcessors集合中。
 *                  3）.将使用者自己实现的BeanDefinitionRegistryPostProcessor实现类和spring内部的BeanDefinitionRegistryPostProcessor
 *              实现类通通聚合到currentRegistryProcessors集合中，接着执行接口中的postProcessBeanDefinitionRegistry方法（方法回调），
 *              在spring内部的实现类回调方法中，也就是ConfigurationClassPostProcessor中完成了对bean的扫描。
 *                  4）.将currentRegistryProcessors集合清空，接着会从bd的map中拿到所有的BeanDefinitionRegistryPostProcessor是实现类，
 *               同时实现了Ordered接口，这里的Ordered接口其实就是决定后面回调执行顺序的，将这些处理器对象放入currentRegistryProcessors
 *               和registryProcessors集合中，接着回调执行处理器中的postProcessBeanDefinitionRegistry方法。
 *                  5）.接着继续清空currentRegistryProcessors集合，从map中拿到剩余的BeanDefinitionRegistryPostProcessor实现类，
 *               将这些处理器对象放入currentRegistryProcessors和registryProcessors集合中，接着回调执行处理器中的postProcessBeanDefinitionRegistry方法。
 *               注意这里之所以会需要循环是因为有可能在回调postProcessBeanDefinitionRegistry方法的时候可能会新注册BeanDefinitionRegistryPostProcessor的bean对象，
 *               这是为了保障找到所有BeanDefinitionRegistryPostProcessor的实现类。
 *                  6）.if语句最后执行registryProcessors和regularPostProcessors集合中处理器的postProcessBeanFactory方法，这里先是执行的BeanDefinitionRegistryPostProcessor实现类
 *               的postProcessBeanFactory方法，再执行的BeanFactoryPostProcessor的postProcessBeanFactory方法。
 *                  7）.最后就是对所有BeanFactoryPostProcessor实现类（除了使用者通过api注入的）执行回调方法，同样是分为三类，实
 *               现PriorityOrdered接口的，实现Ordered接口的和没有实现排序接口的。
 *
 *                  总结：
 *                  1.这个方法中主要是对BeanFactoryPostProcessors和BeanDefinitionRegistryPostProcessor是实现类的回调方法的调用。
 *                  2. 针对的实现类包括通过APIAnnotationConfigApplicationContext.addBeanFactoryPostProcessor()注入的
 *                  BeanFactoryPostProcessors和BeanDefinitionRegistryPostProcessor的实现类，spring内部的
 *                  BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessors实现类，扫描出来的BeanDefinitionRegistryPostProcessor
 *                  和BeanFactoryPostProcessors实现类。
 *                  3.它们的回调方法执行顺序如下：
 *                      1）.通过APIAnnotationConfigApplicationContext.addBeanFactoryPostProcessor()注入的BeanDefinitionRegistryPostProcessor的
 *                      实现类的postProcessBeanDefinitionRegistry方法。
 *                      2）.spring内部的BeanDefinitionRegistryPostProcessor实现类的postProcessBeanDefinitionRegistry方法。
 *                      3）.扫描出来的BeanDefinitionRegistryPostProcessor实现类，且实现了Order接口，执行postProcessBeanDefinitionRegistry方法。
 *                      4）.其余所有其他BeanDefinitionRegistryPostProcessor实现类，执行postProcessBeanDefinitionRegistry方法。
 *                      5）.通过APIAnnotationConfigApplicationContext.addBeanFactoryPostProcessor()注入的BeanDefinitionRegistryPostProcessor的实现类、
 *                      spring内部的BeanDefinitionRegistryPostProcessor实现类和扫描出来的BeanDefinitionRegistryPostProcessor实现类的postProcessBeanFactory方法。
 *                      6）.通过APIAnnotationConfigApplicationContext.addBeanFactoryPostProcessor()注入的BeanFactoryPostProcessors的postProcessBeanFactory方法。
 *                      7）.扫描出来和spring内部的BeanFactoryPostProcessors和PriorityOrdered的实现类的postProcessBeanFactory方法。
 *                      8）.扫描出来和spring内部的BeanFactoryPostProcessors和Ordered的实现类的postProcessBeanFactory方法。
 *                      9）.扫描出来和spring内部的剩余的BeanFactoryPostProcessors实现类的postProcessBeanFactory方法。
 *
 *          3.ApplicationListener：监听容器中发布的事件，实现事件驱动模型的开发
 *              public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *              监听ApplicationEvent及下面的子事件
 *              步骤：
 *                  1）.写一个监听器爱监听某个事件（ApplicationEvent及下面的子事件）
 *
 *                      【@EventListener】
 *                      原理：使用EventListenerMethodProcessor处理器来解析方法上的注解
 *                          SmartInitializingSingleton原理：
 *                          a.容器创建对象并refresh();
 *                          b.finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean
 *                              (1)先创建所有的单实例bean
 *                              (2)获取所有创建好的单实例bean，判断是否是 SmartInitializingSingleton 类型的
 *                                 如果是就调用 afterSingletonsInstantiated();
 *
 *
 *
 *                  2）.把监听器放入到容器中
 *                  3）.只要容器中有相关事件的发布，我们就能监听到这个事件
 *                      ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件
 *                      ContextClosedEvent：关闭容器会发布这个事件
 *                  4）.发布一个自定义事件
 *               原理：
 *                  1）.ContextRefreshedEvent事件：
 *                      a.容器创建对象 refresh();
 *                      b.finishRefresh();容器刷新完成后会发布ContextRefreshedEvent事件
 *                      c.publishEvent(new ContextRefreshedEvent(this));
 *                  2）.自己发布事件
 *                  3）.容器关闭会发布ContextClosedEvent事件
 *                        【事件发布流程】：
 *                            （1）获取事件的多播器（派发器）：getApplicationEventMulticaster()
 *                            （2）multicastEvent派发事件：
 *                            （3）获取到所有的ApplicationListener：
 *                                 for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                                  a.如果有Executor，可以使用Executor进行异步派发
 *                                     Executor executor = getTaskExecutor();
 *                                  b.否则同步方式直接执行listener方法：
 *                                     invokeListener(listener, event);
 *                                     拿到listener回调onApplicationEvent()方法
 *
 *          【事件多播器（派发前）】：
 *              1）容器创建对象：refresh();
 *              2）initApplicationEventMulticaster();初始化 ApplicationEventMulticaster
 *                  a.先去容器找有没有id="applicationEventMulticaster"的组件
 *                  b.如果没有 this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *                    并且加入到容器中，我们就可以在其他组件派发事件时自动注入这个 applicationEventMulticaster
 *
 *          【容器中有哪些监听器】
 *              1）容器创建对象：refresh();
 *              2）registerListeners();
 *                 从容器中拿到所有的监听器：
 *                      String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *                 把他们注册到 ApplicationEventMulticaster 中：
 * 		                for (String listenerBeanName : listenerBeanNames) {
 * 			                getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *                      }
 *
 *
 *
 *
 *
 *
 *
 */


@Configuration
@ComponentScan("dream.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }

}
