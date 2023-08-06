package onboarding.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class LoginRequest {
    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private final String email;

    @NotEmpty(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
