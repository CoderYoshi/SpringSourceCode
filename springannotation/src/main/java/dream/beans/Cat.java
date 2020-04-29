package dream.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//@Component
public class Cat implements InitializingBean, DisposableBean {



    public Cat() {
        System.out.println("Cat的构造器方法...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Cat的afterPropertiesSet初始化方法...");
    }
    public void destroy() throws Exception {
        System.out.println("Cat的destroy销毁方法...");
    }
}
