package com.example.tea.Configuration;

import com.example.tea.Utility.Constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebConfiguration {

    private final BearerTokenAuthFilter bearerTokenAuthFilter;

    public WebConfiguration(BearerTokenAuthFilter bearerTokenAuthFilter) {
        this.bearerTokenAuthFilter = bearerTokenAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public: client website product APIs + register/login + swagger
                        .requestMatchers(
                                "/public/**",
                                "/userMST/login",
                                "/userMST/create",
                                "/userMST/createAdmin",
                                "/itemMST"+Constant.SEARCH,
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // Shop-owner (merchant) only management endpoints
                        .requestMatchers(
                                "/itemMST/**",
                                "/invoiceMST/**",
                                "/purchaseMST/**",
                                "/userMST/**",
                                "/homeImage/**",
                                "/about/**",
                                "/contact/**",
                                "/siteSetting/**",
                                "/enquiry/getAll",
                                "/enquiry/unresolved",
                                "/enquiry/resolve/**"
                        ).hasAuthority("ADMIN")
                        // Everything else (e.g. the customer inquiry cart) just needs a logged-in user
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // client website (any origin in dev)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
