package com.example.portal.controller;

import com.example.portal.dto.*; import com.example.portal.model.User; import com.example.portal.security.JwtService; import com.example.portal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity; import org.springframework.security.authentication.AuthenticationManager; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final UserService users; private final AuthenticationManager am; private final JwtService jwt;
  public AuthController(UserService users, AuthenticationManager am, JwtService jwt){this.users=users; this.am=am; this.jwt=jwt;}
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest r){
    User u = users.register(r.username, r.password);
    String token = jwt.generate(u.getUsername(), Map.of("role", u.getRole()));
    return ResponseEntity.ok(new JwtResponse(token));
  }
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest r){
    Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(r.username, r.password));
    User u = (User) auth.getPrincipal();
    String token = jwt.generate(u.getUsername(), Map.of("role", u.getRole()));
    return ResponseEntity.ok(new JwtResponse(token));
  }
}
