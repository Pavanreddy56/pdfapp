package com.example.portal.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {
  @NotBlank public String username;
  @NotBlank @Size(min=6) public String password;
}
