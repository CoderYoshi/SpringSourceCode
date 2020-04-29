package dream.config;

import dream.beans.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyValueConfig {

    @Bean(value = "person")  //value 指定Bean的id
    public Person person() {
        return new Person();
    }

}
