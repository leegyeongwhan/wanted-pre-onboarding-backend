package onboarding.service;

import onboarding.domain.Board;
import onboarding.domain.Member;
import onboarding.dto.request.BoardRegisterRequest;
import onboarding.dto.request.LoginRequest;
import onboarding.dto.request.SignUpRequest;
import onboarding.repository.BoardRepository;
import onboarding.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private BoardService boardService;


    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void registerTest() {
        long userId = 1L;
        BoardRegisterRequest registerRequest = new BoardRegisterRequest("Title", "Content");
        Member member = new Member("test@example.com", "password");
        member.setId(1L);
        Board board = new Board(registerRequest.getTitle(), registerRequest.getTitle(), member);

        when(memberService.findMemberById(userId)).thenReturn(member);

        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> {
            Board savedBoard = invocation.getArgument(0);
            savedBoard.setId(1L); // Simulate saving the board and setting an ID
            return savedBoard;
        });

        // Act
        Long boardId = boardService.register(registerRequest, userId);
        // Assert
        assertNotNull(boardId);
        verify(memberService).findMemberById(userId);
        verify(boardRepository).save(argThat(savedBoard -> savedBoard.getMember() == member));
    }
}
