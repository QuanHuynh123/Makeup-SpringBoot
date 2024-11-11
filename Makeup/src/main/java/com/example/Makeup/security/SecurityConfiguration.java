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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AccountService accountService;


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//        .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(registry->{
//            registry.requestMatchers(
//                    "/register",
//                    "/login/**",
//                    "/home/**",
//                    "/cosplay/**",
//                    "/cosplay/**",
//                    "/home",
//                    "/api/**",
//                    "/js/**",  /* static resource */
//                    "/css/**",
//                    "/images/**",
//                    "/fonts/**",
//                    "/icon/**",
//                    "/status" // test
//            ).permitAll();
//            //registry.a
//            registry.requestMatchers("/admin/**").hasRole("ADMIN");
//            registry.requestMatchers("/user/**").hasRole("USER");
//            registry.anyRequest().authenticated();
//        })
//        .formLogin(form -> form
//                .loginPage("/login")
//        )
//        .logout(logout -> logout
//            .logoutUrl("/logout")
//            .permitAll()
//        )
//        .sessionManagement(session -> session
//            .maximumSessions(1) // Giới hạn số phiên đăng nhập cùng lúc (tùy chọn)
//            .expiredUrl("/login?expired=true") // URL khi phiên hết hạn (tùy chọn)
//        )
//        .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(
                            "/register",
                            "/login/**",
                            "/home/**",
                            "/cosplay/**",
                            "/cosplay/**",
                            "/home",
                            "/api/**",  /* Cho phép tất cả yêu cầu tới các API */
                            "/js/**",   /* tài nguyên tĩnh */
                            "/css/**",
                            "/images/**",
                            "/fonts/**",
                            "/icon/**",
                            "/status"   // test
                    ).permitAll(); // Cung cấp quyền truy cập không cần xác thực
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.anyRequest().authenticated(); // Các yêu cầu khác cần xác thực
                })
                .formLogin(form -> form
                        .loginPage("/login")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập cùng lúc
                        .expiredUrl("/login?expired=true") // URL khi phiên hết hạn
                )
                .build();
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
