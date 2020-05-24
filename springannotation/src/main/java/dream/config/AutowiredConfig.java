package dream.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配：
 *      spring利用依赖注入，完成对IOC容器中各个组件的依赖关系赋值
 *      1.@Aurowired:自动注入[Spring定义的注解]
 *          1).默认优先按照类型去容器中寻找组件
 *          2).有多个相同类型组件时，以属性的名称作为组件的id去容器中寻找
 *          3).@Qualifier("bookDao"):指定需要装配的组件的id，而不是使用属性名
 *          4).自动装配默认一定要将属性赋值好，没有就会报错；可以使用@Aurowired(required=false)
 *          5).@Primary:自动装配时默认使用首选的bean
 *      2.Spring还支持使用@Resource（JR250）和@Inject（JR330）[java规范的注解]
 *          @Resource:也可以实现自动装配，默认按照组件名称进行装配
 *          @Inject:需要导入javax.inject包，和@Aurowiredf功能一样，没有required=false
 *      AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能
 *      3.@Aurowired:构造器、方法、参数、属性：都是从IOC容器中获取参数组件的值
 *          1).方法:Spring创建当前对象时，会调用方法，完成赋值；方法使用的参数，自定义类型的值从IOC容器中获取
 *          2).构造器：默认加在IOC容器的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
 *                     如果只有一个有参构造器，则可以省略@Aurowired注解
 *      4.自定义组件想要使用Spring容器底层的一些组件（ApplicationContext、BeanFactory，.etc）
 *          自定义组件实现xxxAware接口:在创建对象时会调用接口规定的方法注入相关组件（都是Aware接口的子类）
 *          xxxAware:功能都是由对应的xxxProcessor实现的，例如ApplicationContextAware ====> 例如ApplicationContextAwareProcessor
 *
 */
@Configuration
@ComponentScan({"dream"})
public class AutowiredConfig {



}
