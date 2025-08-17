package com.example.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
  @Bean public CorsFilter corsFilter(){
    CorsConfiguration c = new CorsConfiguration();
    c.addAllowedOriginPattern("*"); c.addAllowedHeader("*"); c.addAllowedMethod("*"); c.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
    s.registerCorsConfiguration("/**", c); return new CorsFilter(s);
  }
}
