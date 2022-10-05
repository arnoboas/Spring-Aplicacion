package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .authorizeRequests()
        .antMatchers(resources).permitAll()  //cualqiera entre a ruta resources acceda
        .antMatchers("/","/index").permitAll()
            .anyRequest().authenticated()//estar autenticado
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/userForm") //log exitoso manda a este url
            .failureUrl("/login?error=true")//log no exitoso lleva al mismo login con error=true
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            .logoutSuccessUrl("/login?logout");
    }
    
    //ENCRIPTADOR
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //para que no de error la creacion del bean se pone una instruccion en el archivoapplication.properties
    //o se puede declarar el metodo como estatico
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);//nivel desde 4 hasta 31
        return bCryptPasswordEncoder;
    }
    
    @Autowired
    UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
}
