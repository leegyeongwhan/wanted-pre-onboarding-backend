package onboarding.service;

import lombok.RequiredArgsConstructor;
import onboarding.domain.Board;
import onboarding.domain.Member;
import onboarding.dto.BoardDetailResponse;
import onboarding.dto.BoardListResponse;
import onboarding.dto.request.BoardRegisterRequest;
import onboarding.repository.BoardRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Transactional
    public Long register(BoardRegisterRequest boardRegisterRequest, long userId) {
        Member member = memberService.findMemberById(userId);
        Board board = boardRegisterRequest.toEntity(member);
        return boardRepository.save(board).getId();
    }

    @Transactional(readOnly = true)
    public BoardListResponse read(Pageable pageable) {
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDetailResponse> collect = boards.stream().
                map(BoardDetailResponse::from).
                collect(Collectors.toList());
        return BoardListResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public BoardDetailResponse detailRead(long userId, long boardId) {
        Member member = memberService.findMemberById(userId);
        Board board = findBoardById(boardId);
        board.validateAccess(member.getId());
        return BoardDetailResponse.from(board);
    }

    @Transactional
    public Long update(BoardRegisterRequest boardRegisterRequest, long userId, long boardId) {
        Member member = memberService.findMemberById(userId);
        Board board = findBoardById(boardId);
        board.validateAccess(member.getId());
        board.update(boardRegisterRequest.getTitle(), boardRegisterRequest.getContent());
        return board.getId();
    }

    @Transactional
    public void delete(long userId, long boardId) {
        Member member = memberService.findMemberById(userId);
        Board board = findBoardById(boardId);
        board.validateAccess(member.getId());
        board.deleted(true);
    }

    public Board findBoardById(long boardId) {
        return boardRepository.findById(boardId).orElseThrow(NotFoundBoard::new);
    }
}
