package onboarding.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
public class SignUpRequest {
    @Email
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}