package onboarding.controller;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String jwtToken;

    private LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public static LoginResponse of(String memberToken) {
        return new LoginResponse(memberToken);
    }
}
