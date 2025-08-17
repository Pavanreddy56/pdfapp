package com.example.portal.controller;

import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/internal")
public class AdminController {
  @GetMapping("/ops")
  public ResponseEntity<String> ops(){ return ResponseEntity.ok("ok:internal-ops"); }
}
