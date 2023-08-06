package onboarding.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginResponse {

    @JsonProperty("jwtToken")
    private final String jwtToken;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public static LoginResponse of(String memberToken) {
        return new LoginResponse(memberToken);
    }
}
