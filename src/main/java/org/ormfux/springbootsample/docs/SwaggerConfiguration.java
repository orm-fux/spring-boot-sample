package org.ormfux.springbootsample.docs;

import java.util.Arrays;

import org.ormfux.springbootsample.webservice.rest.BookRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
//https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//https://springfox.github.io/springfox/docs/current/
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {
    
    @Autowired
    private Environment environment;
    
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      //this should actually be a base class in real cases
                                                      .apis(RequestHandlerSelectors.basePackage(BookRestService.class.getPackage().getName()))
                                                      .paths(PathSelectors.ant(environment.getProperty("restservice.path.root") + "/**"))
                                                      .build()
                                                      .apiInfo(metaData())
                                                      .securitySchemes(Arrays.asList(securityScheme()))
                                                      .securityContexts(Arrays.asList(securityContext()));
    }
    
    //these values should come from some properties in real cases
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("\"Spring Boot REST API for Library\"")
                .version("1.0.0")
                .license("Unlicense")
                .licenseUrl("http://unlicense.org/")
                .contact(new Contact("Orm Fux", "https://github.com/orm-fux/spring-boot", "email@host"))
                .build();
    }
    
    @Override
    protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    //authentication for rest service invocations
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                              .forPaths(PathSelectors.ant(environment.getProperty("restservice.path.root") + "/**"))
                              .securityReferences(Arrays.asList(securityReference()))
                              .build();
    }
    
    private SecurityReference securityReference() {
        return SecurityReference.builder()
                                .reference("securityReferenceName")
                                .scopes(new AuthorizationScope[] { authorizationScope() })
                                .build();
    }
    
    private AuthorizationScope authorizationScope() {
        return new AuthorizationScope("authScope", "Access everything");
    }
    
    private SecurityScheme securityScheme() {
        return new BasicAuth("securityReferenceName");
    }
    
}
