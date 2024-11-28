package com.example.Makeup.security;

import com.example.Makeup.service.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.NullSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private AccountService accountService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/adminT/**").hasRole("ADMIN")
                .requestMatchers("/userT/**").hasRole("USER")
                .requestMatchers("/error").permitAll()

                .anyRequest().permitAll()
        )
        .formLogin(form -> form
                .loginPage("/login")
        )
        .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
        )
        .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Tạo session nếu cần
//                .maximumSessions(1) // Giới hạn số phiên đăng nhập cùng lúc
//                .expiredUrl("/login?expired=true") // URL khi phiên hết hạn
        )
        .anonymous(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return accountService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}

//
//        .authorizeHttpRequests(auth -> auth
//        .requestMatchers("images/**", "js/**", "css/**", "icon/**","fonts/**","cdn-cgi/**").permitAll()
//        )
//
//                .authorizeHttpRequests(registry->{
//        registry.requestMatchers(
//                    "/register",
//                            "/login/**",
//                            "/home/**",
//                            "/cosplay/**",
//                            "/cosplay/**",
//                            "/home",
//                            "/api/**"
//).permitAll();
//            registry.requestMatchers("/admin/**").hasRole("ADMIN");
//            registry.requestMatchers("/user/**").hasRole("USER");
//            registry.anyRequest().authenticated();
//        })