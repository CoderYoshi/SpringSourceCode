package dream.config;

import dream.AOP.LogAspects;
import dream.AOP.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *  AOP：[动态代理]
 *      程序运行期间动态的将某段代码插入到指定方法指定位置进行运行的编程方法
 *      1.导入AOC模块，Spring AOC
 *      2.创建一个业务逻辑（MathCalculator）：在业务逻辑运行的时候将日志进行打印
 *      3.定义一个日志切面类（LogAspects）：切面类的方法需要动态感知MathCalculator.div运行到哪里然后执行
 *          通知方法：
 *              前置通知（@Before）：logStart，在div方法运行之前运行
 *              后置通知（@After）：logEnd，在div方法运行之后运行（无论方法正常结束还是异常结束都调用）
 *              返回通知（@AfterReturning）：logReturn，在div方法正常返回之后运行
 *              异常通知（@AfterThrowing）：logException，在div方法出现异常以后运行
 *              环绕通知（@Around）：动态代理，手动推进目标方法执行
 *      4.给切面类标注通知注解（何时运行）
 *      5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 *      6.给切面类加一个注解@Aspect，告诉Spring这是一个切面类
 *      7.给配置类加@EnableAspectJAutoProxy注解，开启基于注解的aop模式
 *          Spring中有很多@Enablexxx注解：用于开启某些功能
 *
 *
 *   三步：
 *      1.将业务逻辑组件和切面类都加入到容器中，使用@Aspect标明切面类
 *      2.在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *      3.开启注解的AOP模式（@EnableAspectJAutoProxy）
 *
 *
 *  AOP的原理：[看给容器中注入了什么组件，这个组件什么时候工作，功能是什么]
 *      @EnableAspectJAutoProxy
 *          1.@EnableAspectJAutoProxy是什么
 *          @Import({AspectJAutoProxyRegistrar.class})，给容器中导入AspectJAutoProxyRegistrar组件
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean:
 *              以org.springframework.aop.config.internalAutoProxyCreator为beanName向容器中注册了一个
 *              org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator 的beanDefinition
 *          2.AnnotationAwareAspectJAutoProxyCreator:
 *              继承关系：AnnotationAwareAspectJAutoProxyCreator <== AspectJAwareAdvisorAutoProxyCreator <== AbstractAdvisorAutoProxyCreator
 *                    <== AbstractAutoProxyCreator <== ProxyProcessorSupport[抽象类],SmartInstantiationAwareBeanPostProcessor[后置处理器接口], BeanFactoryAware[接口]
 *              关注后置处理器（bean完成初始化前后所做的事情）、自动装配BeanFactory
 *              (1).AbstractAutoProxyCreator.setBeanFactory()：
 *                  (2).AbstractAdvisorAutoProxyCreator.setBeanFactory()重写，调用方法AbstractAdvisorAutoProxyCreator.initBeanFactory()
 *                      (3).AspectJAwareAdvisorAutoProxyCreator
 *                          (4).AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()重写
 *
 *              AbstractAutoProxyCreator.后置处理器逻辑
 *
 *
 *     流程：
 *          1.传入配置类，创建IOC容器
 *          2.注册配置类，调用refresh()刷新容器
 *          3.this.registerBeanPostProcessors(beanFactory)：注册bean的后置处理器来方便拦截bean的创建
 *              1).先获取IOC容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *              2).给容器中添加其他BeanPostProcessor
 *              3).优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *              4).再注册实现了Ordered接口的BeanPostProcessor
 *              5).最后注册其他BeanPostProcessor
 *              6).注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中
 *                      创建名为internalAutoProxyCreator的BeanPostProcessor[类型为AnnotationAwareAspectJAutoProxyCreator]
 *                      a.创建Bean的实例
 *                      b.populateBean(beanName, mbd, instanceWrapper)：给bean的属性赋值
 *                      c.initializeBean(beanName, exposedObject, mbd)：初始化bean
 *                          Ⅰ.invokeAwareMethods(beanName, bean)：处理Aware接口的方法回调
 *                          Ⅱ.applyBeanPostProcessorsBeforeInitialization(bean, beanName)：执行后置处理器的postProcessorsBeforeInitialization()
 *                          Ⅲ.invokeInitMethods(beanName, wrappedBean, mbd)：执行自定义的初始化方法
 *                          Ⅳ.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName)：执行后置处理器的postProcessorsAfterInitialization()
 *                      d.BeanPostProcessor[AnnotationAwareAspectJAutoProxyCreator]创建成功：获得一个aspectJAdvisorsBuilder
 *              7).把BeanPostProcessor注册到BeanFactory：
 *                  beanFactory.addBeanPostProcessor(postProcessor)
 * =================以上是创建AnnotationAwareAspectJAutoProxyCreator的过程=============
 *
 *                  AnnotationAwareAspectJAutoProxyCreator是继承自InstantiationAwareBeanPostProcessor的后置处理器
 *          4.finishBeanFactoryInitialization(beanFactory)：完成beanFactory的初始化，创建剩下的单实例bean
 *              1).遍历获取容器中所有的Bean，依次创建对象getBean(beanName)
 *                  getBean -> doGetBean -> getSingleton()
 *              2).创建bean：【AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前进行拦截，会调用postProcessBeforeInstantiation()方法】
 *                      a.先从缓存中获取当前bean是否被创建，如果能获取到说明已经被创建，可以直接使用，否则再创建；所有被创建的bean都会被缓存起来
 *                      b.createBean()：创建bean，AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean实例
 *                            【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *                            【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *                          Ⅰ.resolveBeforeInstantiation(beanName, mbdToUse):解析BeforeInstantiation
 *                             希望后置处理器在此能返回一个代理对象，如果能返回代理对象就使用，否则继续执行程序
 *                                 (1).后置处理器先尝试返回对象：
 *                                   bean = this.applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                                   拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor，就执行postProcessorsBeforeInstantiation
 *                                   if (bean != null) {
 *                                          bean = this.applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                                      }
 *                          Ⅱ.doCreateBean(beanName, mbdToUse, args)：按照3.6）的流程创建一个bean实例
 *
 *
 *      AnnotationAwareAspectJAutoProxyCreator【AnnotationAwareAspectJAutoProxyCreator】的作用：
 *          1.在任何bean创建之前，调用postProcessBeforeInstantiation()方法（此处关注MathCalculator和LogAspects的创建过程）
 *              1).判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）
 *              2).判断当前bean是否是基础类型Advice.class，Pointcut.class，Advisor.class，AopInfrastructureBean.class，
 *                 或者是否是切面（@Aspect）
 *              3).判断是否需要跳过
 *                  a.获取候选的增强器（切片里面的通知方法） 【List<Advisor> candidateAdvisors】
 *                    （每一个封装的通知方法增强器是InstantiationModelAwarePointcutAdvisor类型）
 *                    判断每一个增强器是否是AspectJPointcutAdvisor类型，返回true
 *                  b.永远返回false
 *          2.创建对象
 *              postProcessAfterInitialization：
 *               return this.wrapIfNecessary(bean, beanName, cacheKey) //需要的情况下进行包装
 *                 1).获取当前bean的所有增强器（通知方法）  Object[] specificInterceptors
 *                     a.找到候选的所有增强器（找那些通知方法是要切入当前bean方法的）
 *                     b.获取到在当前bean使用的增强器
 *                     c.给增强器排序
 *                 2).保存当前bean在advisedBeans中
 *                 3).如果当前bean需要增强，创建当前bean的代理对象
 *                     a.获取所有增强器
 *                     b.保存到proxyFactory
 *                     c.创建代理对象：Spring自动决定
 *                       JdkDynamicAopProxy(config)：jdk的动态代理
 *                       ObjenesisCglibAopProxy(config)：cglib的动态代理
 *                 4).给容器中返回当前组件使用cglib增强了的代理对象
 *                 5).以后容器中获取到的就是这个代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 *         3.目标方法的执行
 *              容器中保存了组件的代理对象（cglib增强后的对象），这个对象里保存了详细信息（如增强器、目标对象，.etc）
 *              1).CglibAopProxy.intercept():拦截目标方法的执行
 *              2).根据ProxyFactory获取拦截器链
 *                  List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *                  a.创建一个List<Object> interceptorList保存所有（5个）拦截器：一个默认ExposeInvocationInterceptor的和4个增强器
 *                  b.b遍历所有的所有的增强器并将其转为Interceptor：interceptors = registry.getInterceptors(advisor);
 *                  c.将增强器转为List<MethodInterceptor>：
 *                      如果是MethodInterceptor直接加入到集合中；如果不是则使用AdvisorAdapter（适配器）将增强器转为MethodInterceptor
 *                      转换完成返回数组
 *              3).如果没有拦截器链，直接执行目标方法
 *                  拦截器链：每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制
 *              4).如果有拦截器链，把需要执行的目标对象、目标方法、拦截器链等信息传入创建一个CglibAopProxy.CglibMethodInvocation
 *                 对象并调用 Object retVal = mi.proceed()
 *              5).拦截器链的触发
 *                  a.如果没有拦截器直接执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（执行到了最后一个拦截器），执行目标方法
 *                  b.链式获取每一个拦截器，拦截器执行invoke()方法,每一个拦截器等待下一个拦截器执行完成返回以后再来执行；拦截器的机制，
 *                    保证通知方法与目标方法的执行顺序
 *
 *   总结：
 *      1.@EnableAspectJAutoProxy开启AOP功能
 *      2.@EnableAspectJAutoProxy会给容器中注册一个AnnotationAwareAspectJAutoProxyCreator组件
 *      3.AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
 *      4.容器的创建流程：
 *          1).registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator对象
 *          2).finishBeanFactoryInitialization()初始化剩下的单实例bean
 *              a.创建业务逻辑组件和切面组件
 *              b.AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 *              c.组件创建完之后，判断组件是否需要增强
 *                  是：切面的通知方法包装成增强器（Advisor）；给业务逻辑组件创建一个代理对象（jdk/cglib）
 *          5).执行目标方法：
 *              a.代理对象执行目标方法：
 *              b.CglibAopProxy.intercept()
 *                  Ⅰ.得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *                  Ⅱ.利用拦截器的链式机制，依次进入每一个拦截器进行执行
 *                  Ⅲ.效果：
 *                      前置通知->目标方法->后置通知->返回通知
 *                      前置通知->目标方法->后置通知->异常通知
 *
 *
 */

@EnableAspectJAutoProxy
@Configuration
public class AOPConfig {

    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }

}
