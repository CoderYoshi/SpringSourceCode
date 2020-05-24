package dream.beans;

import org.springframework.stereotype.Component;

@Component
public class Blue {


        public Blue(){
        System.out.println("Blue的构造器方法...");
    }

    public void init(){
        System.out.println("Blue的初始化方法...");
    }

    public void destroy(){
        System.out.println("Blue的销毁方法...");
    }
}
