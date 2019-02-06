package org.ormfux.springbootsample.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private Environment environment;
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic()
            .and().csrf().disable()
            
            .authorizeRequests()
            
            //It's kinda confusing that these role checks ALWAYS prepend "ROLE_" to the provided roles.
            //No configuration possible. This also results in inconsistent notation (here vs. @Secured)
            .antMatchers(environment.getProperty("restservice.path.root") + "/**").hasAnyRole("USER");
    }
    
}