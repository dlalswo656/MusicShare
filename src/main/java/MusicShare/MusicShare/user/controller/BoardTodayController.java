package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.BoardTodayDTO;
import MusicShare.MusicShare.user.entity.BoardTodayEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.BoardTodayRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.BoardTodayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardTodayController {

    private final BoardTodayRepository boardTodayRepository;
    private final BoardTodayService boardTodayService;
    private final UserRepository userRepository;

    // 오늘의 음악
    @GetMapping("/Today")
    public String TodayBoard(Model model, HttpSession session, @PageableDefault(size = 10) Pageable pageable) {
        // 페이징 처리
        Page<BoardTodayDTO> boardTodayDTOList = boardTodayService.findAll(pageable);

        // 다음 페이지로 넘어가는 링크 생성 코드
        int currentPage = boardTodayDTOList.getNumber(); // 현재 페이지 번호
        int totalPages = boardTodayDTOList.getTotalPages(); // 전체 페이지
        int prevPage = currentPage > 0 ? currentPage -1 : 0; // 이전 페이지
        int nextPage = currentPage + 1 < totalPages ? currentPage + 1 : totalPages -1; // 다음 페이지
        int startPage = currentPage / 10 * 10 + 1; // 시작 페이지
        int endPage = Math.min(startPage + 9, totalPages); // 끝 페이지
        String url = "/Board/Today?page="; // 다음 페이지로 넘어가는 링크 URL

        model.addAttribute("currentPage", currentPage); // 현재 페이지
        model.addAttribute("totalPages", totalPages); // 전체 페이지
        model.addAttribute("prevPageUrl", prevPage); // 이전 페이지
        model.addAttribute("nextPageUrl", nextPage); // 다음 페이지
        model.addAttribute("startPage", startPage); // 시작 페이지
        model.addAttribute("endPage", endPage); // 끝 페이지
        model.addAttribute("url", url);

        // DB에 게시글 데이터를 가져옴
        model.addAttribute("boardTodayList", boardTodayDTOList);
        return "board/Today";
    }

    // 게시글 작성
    @GetMapping("/TodayWrite")
    public String TodayWrite(BoardTodayDTO boardTodayDTO, HttpSession session) {
        // 비로그인 유저가 글작성을 누르면 Login
        Long LoginId = (Long) session.getAttribute("LoginId");
        if (LoginId == null) {
            return "redirect:/Login";
        }
        return "board/TodayWrite";
    }

    @PostMapping("/TodayWrite")
    public String TodaySave(@ModelAttribute BoardTodayDTO boardTodayDTO, HttpSession session) {
        String nonce = ""; // 임시 변수 선언
        Long userId = (Long) session.getAttribute("LoginId"); // 로그인 한 유저의 Id값
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        UserEntity user = userOpt.orElse(null);
        System.out.println("BoardTodayDTO : " + boardTodayDTO);

        // 게시글 저장
        BoardTodayEntity boardTodayEntity = boardTodayService.toBoardTodayEntity(boardTodayDTO, user);
        boardTodayService.TodaySave(boardTodayDTO, user);
        return "redirect:/Board/Today";
    }

    // 게시글 정보
    @GetMapping("/Today/{id}")
    public String TodayId(@PathVariable Long id, Model model) {
        boardTodayService.TodayHits(id);
        BoardTodayDTO boardTodayDTO = boardTodayService.TodayId(id);
        model.addAttribute("boardToday", boardTodayDTO);

        System.out.println("유저의 아이디" + boardTodayDTO); // 유저의 id boardTodayDTO로 잘 가져오는 지 테스트
        return "board/TodayDetail";
    }

    // 게시글 수정
    @GetMapping("/Update/{id}")
    public String TodayUpdate(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<BoardTodayEntity> boardOpt = boardTodayRepository.findById(id);
        if (boardOpt.isPresent()) {
            BoardTodayEntity boardTodayEntity = boardOpt.get();
            BoardTodayDTO boardTodayDTO = boardTodayService.TodayId(id);

            // 현재 로그인한 사용자의 아이디를 BoardTodayDTO에 설정
            Long userId = (Long) session.getAttribute("LoginId");
            if (!boardTodayEntity.getUser().getId().equals(userId)) {
                return "redirect:/Board/Today";
            }

            boardTodayDTO.setUserId(userId);
            model.addAttribute("todayUpdate", boardTodayDTO);
        }

        return "board/TodayUpdate";
    }

    @PostMapping("/TodayUpdate")
    public String UpdateToday(Long id, @ModelAttribute BoardTodayDTO boardTodayDTO) {
        boardTodayService.UpdateToday(id, boardTodayDTO);
        return "redirect:/Board/Today";
    }
    
    // 게시글 삭제
    @GetMapping("/Delete/{id}")
    public String Delete(@PathVariable Long id, HttpSession session) {
        // 로그인한 유저 id 값
        Long userId = (Long) session.getAttribute("LoginId");
        Optional<BoardTodayEntity> boardOpt = boardTodayRepository.findById(id);
        if (boardOpt.isPresent()) {
            BoardTodayEntity boardTodayEntity = boardOpt.get();
            // 현재 로그인한 사용자가 작성자가 아닌 경우에는 삭제 불가능
            if (!boardTodayEntity.getUser().getId().equals(userId)) {
                return "redirect:/Board/Today";
            }
            boardTodayService.Delete(id);
    }
        return "redirect:/Board/Today";
    }
}
