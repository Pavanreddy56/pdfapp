package com.example.portal.service;

import com.example.portal.model.User;
import com.example.portal.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository repo; private final PasswordEncoder encoder;
  public UserService(UserRepository repo, PasswordEncoder encoder){ this.repo=repo; this.encoder=encoder; }
  public User register(String username, String rawPwd){
    User u = new User(); u.setUsername(username); u.setPassword(encoder.encode(rawPwd)); return repo.save(u);
  }
  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
  }
}
