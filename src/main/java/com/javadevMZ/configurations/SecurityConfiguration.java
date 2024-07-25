package com.javadevMZ.configurations;

import com.javadevMZ.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
   public UserDetailsService userDetailsManager(UserRepository repository){
    final List<UserDetails> users = new ArrayList<>();
        repository.findAll().forEach((x) ->{
            UserDetails userDetails = User
                    .withUsername(x.getEmail())
                    .password(passwordEncoder().encode(x.getPassword()))
                    .roles(x.getRole().toString())
                    .build();
          users.add(userDetails);
        });
        UserDetailsService result = new InMemoryUserDetailsManager(users);
   return result;
    }

    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                      .requestMatchers(HttpMethod.POST, "/orders/**").hasAnyRole("CASHIER","SENIOR_CASHIER")
                       .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyRole("CASHIER","SENIOR_CASHIER")
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasRole("SENIOR_CASHIER")
                        .requestMatchers(HttpMethod.GET, "/orders/*").hasAnyRole("CASHIER","SENIOR_CASHIER")
                        .requestMatchers("/new_order").hasAnyRole("CASHIER","SENIOR_CASHIER")
                        .requestMatchers("/z-report", "/x-report").hasRole("SENIOR_CASHIER")
                        .requestMatchers("/warehouse").hasRole("COMMODITY_EXPERT")
                        .requestMatchers("/new_user").hasRole("ADMIN")
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
