package org.ormfux.springbootsample.webservice.rest;

import javax.annotation.Priority;

import org.apache.camel.model.rest.RestBindingMode;
import org.ormfux.springbootsample.spring.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//https://github.com/apache/camel/tree/master/examples/camel-example-spring-boot-rest-jpa
//https://github.com/apache/camel/tree/master/examples/camel-example-spring-boot-rest-swagger
@Component
@Priority(500)
@PropertySource(value = "classpath:/camel.yml", name = "camelconfig", factory = YamlPropertySourceFactory.class)
public class BaseCamelConfiguration extends AbstractCamelRestConfiguration {
    
    @Autowired
    private EntityAndDTOConverters converters;
    
    @Override
    public void configure() throws Exception {
        getContext().getTypeConverterRegistry().addTypeConverters(converters);
        
        restConfiguration().component("servlet")
                           .contextPath(config("camel.rest.service.root"))
                           .bindingMode(RestBindingMode.json)
                           .dataFormatProperty("prettyPrint", "true")
                           
                           .apiContextPath(config("camel.rest.api.root"))
                           .apiProperty("api.title", config("camel.rest.api.title"))
                           .apiProperty("api.version", config("camel.rest.api.version"));
        
    }
    
}
