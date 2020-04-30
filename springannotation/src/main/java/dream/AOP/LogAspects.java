package dream.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 切面类
 */
@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    //1.本类引用;@After("pointCut()")
    //2.其他切面类引用：@After("dream.AOP.LogAspects.pointCut()")
    @Pointcut("execution(public int dream.AOP.MathCalculator.*(..))")
    public void pointCut(){

    }
    //JoinPoint一定要出现在参数表的第一位
    //@Before:在目标方法之前切入；切入点表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName()+"运行...参数列表是：{" + Arrays.asList(args) +"}");
    }

    @After("dream.AOP.LogAspects.pointCut()")
    public void logEnd(){
        System.out.println("除法结束...");
    }

    @AfterReturning(value = "pointCut()",returning = "result") //returning:接收返回值
    public void logReturn(Object result){
        System.out.println("除法返回....返回参数是：{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()",throwing = "exception") //throwing:接收异常信息
    public void logException(Exception exception){
        System.out.println("除法异常....异常信息是：{"+exception+"}");
    }
}
