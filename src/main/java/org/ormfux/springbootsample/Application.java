package org.ormfux.springbootsample;

import org.ormfux.springbootsample.spring.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:/pathmappings.yml", name = "pathmappings", factory = YamlPropertySourceFactory.class)
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
