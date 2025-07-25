package com.example.Makeup.security;

import com.example.Makeup.service.impl.AccountServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for stateless APIs
                .authorizeHttpRequests(request -> request
                        // Endpoint not requiring authentication
                        .requestMatchers(
                                "/home","/", "/cosplay/**","/makeup",
                                "/error/**",
                                "/productDetail/**",
                                "/register/**", "/login/**",
                                "/static/**", "/js/**", "/css/**", "/icon/**", "/images/**", "/fonts/**",
                                "api/appointments/create","api/appointments/by-date/**",
                                "/api/category/**"
                        ).permitAll()
                        .requestMatchers(
                                "/admin/**",
                                "/api/admin/order/**"
                        ).hasAnyRole( "ADMIN", "STAFF")
                        .requestMatchers(
                                "/api/accounts/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/user/**",
                                "/api/user/**"
                        ).hasAnyRole("USER", "ADMIN", "STAFF")
                        .anyRequest().authenticated()
                )// Custom access denied page

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session management to stateless
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before UsernamePasswordAuthenticationFilter
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                                    response.sendRedirect("/error/403");
                                })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(new CustomLogoutHandler()) // Use custom logout handler
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login"); // Redirect to login page after logout
                        })
                        .permitAll() // Allow all users to access the logout endpoint
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(accountServiceImpl);

        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // or specify your frontend URL
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }







/*
    Authentication with form login customization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(registry -> {
            registry.requestMatchers("/home",
                            "/cosplay",
                            "/productDetail",
                            "/makeup",
                            "/register/**",
                            "/static/**",
                            "/js/**",
                            "/css/**",
                            "/icon/**",
                            "/images/**",
                            "/fonts/**")
                    .permitAll();
            registry.requestMatchers("/admin/**","/api/accounts/**"
                    ,"api/admin/order/**").hasRole("ADMIN");
            registry.requestMatchers("/user/**","/api/user/**").hasRole("USER");
            registry.anyRequest().permitAll();
        })
        .formLogin(form -> form
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler())
                .permitAll()
        )
        .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll()
        )
        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return accountService;
    }
*/

}