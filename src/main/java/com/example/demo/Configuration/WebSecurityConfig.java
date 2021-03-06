package com.example.demo.Configuration;

import com.example.demo.Roles.Role;
import com.example.demo.Services.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private DetailsService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/forgetPassword").permitAll()
                    .antMatchers("/forgetPasswordAction").permitAll()
                    .antMatchers("/resetPassword").permitAll()
                    .antMatchers("/resetPassword/*").permitAll()
                    .antMatchers("/resetPasswordAction").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/registrationAction").permitAll()
                    .antMatchers("/activate/*").permitAll()
                    .antMatchers("/end").permitAll()
                .antMatchers("/admin/**").hasAuthority(String.valueOf(Role.ADMIN))
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(new CustomFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                    .formLogin()
                    .loginPage("/login").permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Bean("pass")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
