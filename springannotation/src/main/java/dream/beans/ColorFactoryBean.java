package dream.beans;

import org.springframework.beans.factory.FactoryBean;
//创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    //返回一个Color对象，并添加到容器内
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...");
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }
    //控制作用域
    public boolean isSingleton() {
        return false;
    }
}
