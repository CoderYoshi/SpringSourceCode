package dream.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

//@Component
public class Car implements ApplicationContextAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    public void printApplicationContext(){
        System.out.println(applicationContext);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


//    public Car(){
//        System.out.println("Car的构造器方法...");
//    }
//
//    public void init(){
//        System.out.println("Car的初始化方法...");
//    }
//
//    public void destroy(){
//        System.out.println("Car的销毁方法...");
//    }



    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String s = stringValueResolver.resolveStringValue("所以暂时将你眼睛闭了起来${os.name}");
        System.out.println(s);
    }


    @Override
    public String toString() {
        return "Car{" +
                "applicationContext=" + applicationContext +
                '}';
    }
}
