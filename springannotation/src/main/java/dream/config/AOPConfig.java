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
