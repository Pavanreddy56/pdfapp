package com.example.portal.config;

import com.example.portal.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService uds;
  public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService uds){ this.jwtAuthFilter=jwtAuthFilter; this.uds=uds; }
  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean AuthenticationManager authenticationManager(){
    DaoAuthenticationProvider p = new DaoAuthenticationProvider();
    p.setUserDetailsService(uds); p.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(p);
  }
  @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(reg->reg
           .requestMatchers("/api/auth/**").permitAll()
           .requestMatchers("/api/internal/**").hasRole("ADMIN")
           .anyRequest().authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
