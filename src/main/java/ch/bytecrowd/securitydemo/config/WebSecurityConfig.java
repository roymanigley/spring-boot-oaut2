package ch.bytecrowd.securitydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // is needed to support HTTP PUT, POST and DELETE
            //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //.and()
            .authorizeRequests()
                // .antMatchers("/api/*").permitAll()
                .anyRequest().authenticated()
        .and()
            .logout()
                .permitAll()
        .and()
            .oauth2Login();
    }
}