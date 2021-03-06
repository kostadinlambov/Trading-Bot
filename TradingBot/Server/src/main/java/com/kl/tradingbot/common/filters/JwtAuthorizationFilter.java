package com.kl.tradingbot.common.filters;

import com.kl.tradingbot.user.infrastructure.persistence.utils.UserDetailsUtils;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final UserDetailsUtils userDetailsUtils;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
      UserDetailsUtils userDetailsUtils) {
    super(authenticationManager);
    this.userDetailsUtils = userDetailsUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken token = this.getAuthentication(request);

    SecurityContextHolder.getContext().setAuthentication(token);

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

    if (token != null) {
      String username = Jwts.parser()
          .setSigningKey("Secret".getBytes())
          .parseClaimsJws(token.replace("Bearer ", ""))
          .getBody()
          .getSubject();

      if (username != null) {
        UserDetails userData = userDetailsUtils
            .loadUserByUsername(username);

        usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(
            username,
            null,
            userData.getAuthorities()
        );
      }
    }

    return usernamePasswordAuthenticationToken;
  }
}
