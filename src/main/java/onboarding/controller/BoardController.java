package onboarding.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onboarding.dto.BoardDetailResponse;
import onboarding.dto.BoardListResponse;
import onboarding.dto.request.BoardRegisterRequest;
import onboarding.security.LoginCheck;
import onboarding.security.LoginValue;
import onboarding.service.BoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @LoginCheck
    @PostMapping
    public ResponseEntity<Long> register(final @Valid @RequestBody BoardRegisterRequest boardRegisterRequest,
                                         @LoginValue long userId) {
        return ResponseEntity.ok().body(boardService.register(boardRegisterRequest, userId));
    }

    @LoginCheck
    @GetMapping
    public ResponseEntity<BoardListResponse> read(Pageable pageable) {
        return ResponseEntity.ok().body(boardService.read(pageable));
    }

    @LoginCheck
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> detailRead(@LoginValue long userId,
                                                          @PathVariable long boardId) {
        return ResponseEntity.ok().body(boardService.detailRead(userId, boardId));
    }

    @LoginCheck
    @PutMapping("/{boardId}")
    public ResponseEntity<Long> update(final @Valid @RequestBody BoardRegisterRequest boardRegisterRequest,
                                       @LoginValue long userId,
                                       @PathVariable long boardId) {
        return ResponseEntity.ok().body(boardService.update(boardRegisterRequest, userId, boardId));
    }

    @LoginCheck
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@LoginValue long userId,
                                       @PathVariable long boardId) {
        boardService.delete(userId,boardId);
        return ResponseEntity.ok().build();
    }
}
