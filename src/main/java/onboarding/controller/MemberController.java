package onboarding.controller;

import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onboarding.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(final @Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(memberService.signup(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(final @Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(memberService.login(loginRequest));
    }
}
