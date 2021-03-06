package com.kl.tradingbot.common.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kl.tradingbot.common.exception.ErrorCodeEnum;
import com.kl.tradingbot.common.exception.ErrorMessageEnum;
import com.kl.tradingbot.common.exception.TradingBotException;
import com.kl.tradingbot.user.infrastructure.common.model.UserLoginBindingModel;
import com.kl.tradingbot.user.infrastructure.persistence.entities.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper mapper;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper mapper) {
    this.authenticationManager = authenticationManager;
    this.mapper = mapper;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    UserLoginBindingModel loginBindingModel = null;
    try {
      loginBindingModel = new ObjectMapper()
          .readValue(request.getInputStream(), UserLoginBindingModel.class);
    } catch (IOException e) {
      throw new TradingBotException(e.getMessage(), ErrorCodeEnum.TECHNICAL_ERROR);
    }

    return this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginBindingModel.getUsername(),
            loginBindingModel.getPassword(),
            new ArrayList<>()));
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    UserEntity user = ((UserEntity) authResult.getPrincipal());
    String authority = user.getAuthorities()
        .stream()
        .findFirst()
        .orElseThrow(
            () -> new TradingBotException(
                ErrorMessageEnum.UNAUTHORIZED_SERVER_ERROR.getMessage(),
                ErrorCodeEnum.UNAUTHORIZED_SERVER))
        .getAuthority();

    String id = user.getId();
    String profilePicUrl = user.getProfilePicUrl();
    String firstName = user.getFirstName();

    String token = Jwts.builder()
        .setSubject(user.getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 1200000000))
        .claim("role", authority)
        .claim("id", id)
        .claim("profilePicUrl", profilePicUrl)
        .claim("firstName", firstName)
        .signWith(SignatureAlgorithm.HS256, "Secret".getBytes())
        .compact();

    String tokenJson = this.mapper.writeValueAsString("token " + token);

//        response.getWriter()
//                .append("Authorization: Bearer " + token);
    response.getWriter().append(tokenJson);

    if (request.getMethod().equals("POST") && request.getRequestURI().endsWith("/login")) {
      String username = user.getUsername();

      LOGGER.info("User with username {} logged in", username);
    }

    response.addHeader("Authorization", "Bearer " + token);
  }
}
