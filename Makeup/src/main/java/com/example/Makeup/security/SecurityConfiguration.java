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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(registry->{
            registry.requestMatchers(
                    "/register/**",
                    "/home",
                    "/api/**",
                    "/js/**",
                    "/css/**",
                    "/images/**",
                    "/fonts/**",
                    "/icon/**"
            ).permitAll();
            registry.requestMatchers("/admin/**").hasRole("ADMIN");
            registry.requestMatchers("/user**").hasRole("USER");
            registry.anyRequest().authenticated();
        })
        .formLogin(form ->
            form
                .loginPage("/login") // Chỉ định URL của trang login
                //.successHandler()
                .permitAll()         // Cho phép tất cả mọi người truy cập trang login
        )
        .logout(logout -> logout
            .logoutUrl("/logout")              // URL cho logout
            .logoutSuccessUrl("/login?logout=true") // Đường dẫn khi logout thành công
            .permitAll()
        )
        .sessionManagement(session -> session
            .maximumSessions(1) // Giới hạn số phiên đăng nhập cùng lúc (tùy chọn)
            .expiredUrl("/login?expired=true") // URL khi phiên hết hạn (tùy chọn)
        )
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails normalUser = User.builder()
//                .username("user")
//                .password("$2a$12$cO0n/RjOyVf8UNlFIivRVuMxjSSEdwM.iWb7PCnLKBaEYHAP4vzlO")
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User.builder()
//                .username("admin")
//                .password("$2a$12$cO0n/RjOyVf8UNlFIivRVuMxjSSEdwM.iWb7PCnLKBaEYHAP4vzlO")
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }

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
