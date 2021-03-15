package com.kl.tradingbot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kl.tradingbot.common.filters.JwtAuthenticationFilter;
import com.kl.tradingbot.common.filters.JwtAuthorizationFilter;
import com.kl.tradingbot.user.infrastructure.persistence.utils.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration
    extends WebSecurityConfigurerAdapter {

  private final UserDetailsUtils userDetailsUtils;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ObjectMapper mapper;

  @Autowired
  public ApplicationSecurityConfiguration(UserDetailsUtils userDetailsUtils,
      BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper mapper) {
    this.userDetailsUtils = userDetailsUtils;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.mapper = mapper;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/api/user/register",
//            "/**",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        ).permitAll()
        .antMatchers(
            "/api/user/details/*",
            "/api/user/update/*",
            "/socket/**"
        ).hasAnyAuthority("ADMIN", "ROOT", "USER")
        .antMatchers(
            "/api/user/promote",
            "/api/user/demote",
            "/api/user/all/*",
            "/api/user/details/username",
            "/api/logs/all",
            "/api/logs/findByUserName/*"
        ).hasAnyAuthority("ADMIN", "ROOT")
        .antMatchers(
            "/api/user/delete/*",
            "/api/logs/clear",
            "/api/logs/clearByName/*"
        ).hasAuthority("ROOT")
        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyAuthority("ADMIN", "ROOT", "USER")
        .anyRequest().authenticated()
        .and()
        .addFilter(
            new JwtAuthenticationFilter(authenticationManager(), this.mapper))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userDetailsUtils))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/socket/**");
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
    config.setAllowCredentials(true);
//    config.addAllowedOrigin("*");
//    config.addAllowedOriginPattern("*");
    config.addAllowedOriginPattern("http://localhost:3000");
    config.addAllowedHeader("*");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    source.registerCorsConfiguration("/**"
        , config);
    return source;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(this.userDetailsUtils)
        .passwordEncoder(this.bCryptPasswordEncoder);
  }
}
