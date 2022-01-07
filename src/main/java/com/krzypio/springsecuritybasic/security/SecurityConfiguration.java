package com.krzypio.springsecuritybasic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configurationForH2Console(http);

        //to enable posts and resolve 403 Forbidden error
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/myUser").authenticated()
                .antMatchers("/myAccount").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users").hasRole("ADMIN");
        http.formLogin();
        http.httpBasic();

    }

    private void configurationForH2Console(HttpSecurity http) throws Exception {
        //It must be on the beggining of the configure due to http.csrf() declaration. Otherwise 403 Forbidden error for posts.
        // we need config just for console, nothing else
        http.authorizeRequests().antMatchers("/h2_console/**").permitAll();
        // this will ignore only h2-console csrf, spring security 4+
        http.csrf().ignoringAntMatchers("/h2-console/**");
        //this will allow frames with same origin which is much more safe
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
