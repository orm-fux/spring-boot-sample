package org.ormfux.springbootsample.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class GlobalAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
    
    @Override
    public void init(final AuthenticationManagerBuilder auth) throws Exception {
        super.init(auth);
        
        auth.userDetailsService(getUserDetailsService());
    }
    
//    @Bean
//    protected PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); 
//    }
    
    @Bean
    protected UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            
            @Override
          // by default is using a delegatingpasswordencoder. in that case need to specify "{noop}" encryption used for the pws.
            public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
                if (username != null) {
                    switch (username) {
                        case "admin": 
                            return new User("admin", "{noop}adminadmin", AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
                        case "librarian": 
                            return new User("librarian", "{noop}library", AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_LIBRARIAN"));
                    }
                }
                
                throw new UsernameNotFoundException("Could not find user " + username);
            }
        };
    }
}
