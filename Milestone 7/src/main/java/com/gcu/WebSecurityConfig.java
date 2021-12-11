package com.gcu;
 

import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
     
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    	  auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
          .dataSource(dataSource)
          .usersByUsernameQuery("select username, password, enabled from users where username=?")
          .authoritiesByUsernameQuery("select username, role from users where username=?")
      ;

    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
     
      // <input name="_csrf" type="hidden" value="5e650aab-4895-8a73-f9097f5f8cae">
    	.antMatchers("/resources/**", "/webjars/**","/assets/**").permitAll()
        .antMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login/")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll()
        .defaultSuccessUrl("/productCreation/admin")
        .and()
        .logout()
        //.logoutUrl("/login/")
        //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login/")
        .deleteCookies("my-remember-me-cookie")
           .permitAll()
          // .and()
//        .rememberMe()
//         //.key("my-secure-key")
//         .rememberMeCookieName("my-remember-me-cookie")
//         .tokenRepository(persistentTokenRepository())
//         .tokenValiditySeconds(24 * 60 * 60)
//         .and()
//       .exceptionHandling()
//       
     //	.and().csrf()
         ;

    	

  
    	//.ignoringAntMatchers("/api/**");
      
    	    
    }
    PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
       }
    

}




    


 
    