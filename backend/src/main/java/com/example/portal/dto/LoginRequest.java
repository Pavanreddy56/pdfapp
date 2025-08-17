package com.example.portal.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {
  @NotBlank public String username; @NotBlank public String password;
}
