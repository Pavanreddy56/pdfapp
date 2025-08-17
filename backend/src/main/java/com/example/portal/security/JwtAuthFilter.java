package com.example.portal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwtService; private final UserDetailsService uds;
  public JwtAuthFilter(JwtService jwtService, UserDetailsService uds){ this.jwtService=jwtService; this.uds=uds; }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws ServletException, IOException {
    String header = req.getHeader(HttpHeaders.AUTHORIZATION);
    if(header!=null && header.startsWith("Bearer ")){
      String token = header.substring(7);
      try{
        var claims = jwtService.parse(token).getBody();
        String username = claims.getSubject();
        UserDetails ud = uds.loadUserByUsername(username);
        var auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }catch(Exception ignored){}
    }
    chain.doFilter(req,res);
  }
}
