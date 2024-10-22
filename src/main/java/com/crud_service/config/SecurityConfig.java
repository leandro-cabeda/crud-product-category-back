package com.crud_service.config;

import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableWebSecurity
public class SecurityConfig { //extends WebSecurityConfigurerAdapter {

    //@Override
    //protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic().disable().csrf().disable().sessionManagement()
          //      .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            //    .authorizeRequests()
             //   .antMatchers().permitAll();
                //.antMatchers("/swagger*/**", "/api-docs/**", "/configuration/**",
                        //"/webjars/**", "/v2/api-docs/**", "/swagger-ui.html",
                        //"/v2/api-docs", "/auth/login")
                //.permitAll()
                //.antMatchers("/categories/**", "/products/**")
                //.permitAll();
                /*.hasRole("ADMIN")
                .anyRequest()
                .authenticated();*/

     //   http.httpBasic();
   // }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }*/

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}



