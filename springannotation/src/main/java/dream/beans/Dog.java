package dream.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    public Dog() {
        System.out.println("Dog的构造器方法...");
    }
    //对象创建并赋值之后调用
    @PostConstruct
    public void init() throws Exception {
        System.out.println("Dog的 @PostConstruct初始化方法...");
    }
    //容器移除对象之前
    @PreDestroy
    public void destroy() throws Exception {
        System.out.println("Dog的 @PreDestroy销毁方法...");
    }


}
