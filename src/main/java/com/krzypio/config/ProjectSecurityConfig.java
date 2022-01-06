package com.krzypio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
                .antMatchers("/contact", "/notices").permitAll();
        http.formLogin();
        http.httpBasic();
        
        configurationForH2Console(http);
    }

    private void configurationForH2Console(HttpSecurity http) throws Exception {
        // we need config just for console, nothing else
        http.authorizeRequests().antMatchers("/h2_console/**").permitAll();
        // this will ignore only h2-console csrf, spring security 4+
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //this will allow frames with same origin which is much more safe
        http.headers().frameOptions().sameOrigin();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("12345").authorities("admin")
//                .and()
//                .withUser("user").password("12345").authorities("read")
//                .and()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
            UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
            UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
            userDetailsService.createUser(admin);
            userDetailsService.createUser(user);
            auth.userDetailsService(userDetailsService);
            //passwordEncoder bean must be defined.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
