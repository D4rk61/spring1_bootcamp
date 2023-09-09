package dev.blackshark.security.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginUsuarioDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
