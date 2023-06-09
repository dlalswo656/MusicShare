package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.BoardNoticeDTO;
import MusicShare.MusicShare.user.entity.BoardNoticeEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.BoardNoticeRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.BoardNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Board")
public class BoardNoticeController {

    private final BoardNoticeRepository boardNoticeRepository;
    private final BoardNoticeService boardNoticeService;
    private final UserRepository userRepository;

    // 공지사항
    @GetMapping("/Notice")
    public String Notice(Model model, HttpSession session, @PageableDefault(size = 10) Pageable pageable) {

        // 페이징
        Page<BoardNoticeDTO> noticeDTOList = boardNoticeService.findAll(pageable);

        // 다음 페이지로 넘어가는 링크 생성 코드
        int currentPage = noticeDTOList.getNumber(); // 현재 페이지 번호
        int totalPages = noticeDTOList.getTotalPages(); // 전체 페이지
        int prevPage = currentPage > 0 ? currentPage -1 : 0; // 이전 페이지
        int nextPage = currentPage + 1 < totalPages ? currentPage + 1 : totalPages -1; // 다음 페이지
        int startPage = currentPage / 10 * 10 + 1; // 시작 페이지
        int endPage = Math.min(startPage + 9, totalPages); // 끝 페이지
        String url = "/Board/Notice?page="; // 다음 페이지로 넘어가는 링크 URL

        model.addAttribute("currentPage", currentPage); // 현재 페이지
        model.addAttribute("totalPages", totalPages); // 전체 페이지
        model.addAttribute("prevPageUrl", prevPage); // 이전 페이지
        model.addAttribute("nextPageUrl", nextPage); // 다음 페이지
        model.addAttribute("startPage", startPage); // 시작 페이지
        model.addAttribute("endPage", endPage); // 끝 페이지
        model.addAttribute("url", url);

        // DB에 게시글 데이터를 가져옴
        model.addAttribute("boardNoticeList", noticeDTOList);
        return "admin/Notice";
    }

    // 게시글 작성
    @GetMapping("/NoticeWrite")
    public String NoticeWrite(BoardNoticeDTO boardNoticeDTO, HttpSession session) {

        // 비로그인 유저가 글작성을 누르면 Login
        Long LoginId = (Long) session.getAttribute("LoginId");
        if (LoginId == null) {
            return "redirect:/Login";
        }
        return "admin/NoticeWrite";
    }

    @PostMapping("/NoticeWrite")
    public String NoticeSave(@ModelAttribute BoardNoticeDTO boardNoticeDTO, HttpSession session) {
        String nonce = ""; // 임시 변수 선언
        Long userId = (Long) session.getAttribute("LoginId"); // 로그인 한 유저의 Id값
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        UserEntity user = userOpt.orElse(null);
        System.out.println("BoardNoticeDTO : " + boardNoticeDTO);

        // 게시글 저장
        BoardNoticeEntity boardNoticeEntity = boardNoticeService.toBoardNoticeEntity(boardNoticeDTO, user);
        boardNoticeService.NoticeSave(boardNoticeDTO, user);
        return "redirect:/Board/Notice";
    }

    // 게시글 정보
    @GetMapping("/Notice/{id}")
    public String NoticeId(@PathVariable Long id, Model model, HttpSession session) {
        Long LoginId = (Long) session.getAttribute("LoginId");
        model.addAttribute("LoginId", LoginId);
        System.out.println("LoginId ?" + LoginId); // LoginId에 값이 잘 가져오는 지 디버깅

        String LoginName = (String) session.getAttribute("LoginName");
        model.addAttribute("LoginName", LoginName);
        System.out.println("고요" + LoginName); // LoginName에 name 값이 잘 가져오는 지 디버깅

        boardNoticeService.NoticeHits(id);
        BoardNoticeDTO boardNoticeDTO = boardNoticeService.NoticeId(id);
        model.addAttribute("boardNotice", boardNoticeDTO); // boardTodayDTO에 값이 잘 가져오는 지 디버깅

        System.out.println("boardNoticeDTO" + boardNoticeDTO); // 유저의 id boardTodayDTO로 잘 가져오는 지 테스트
        return "admin/NoticeDetail";
    }

    // 게시글 수정
    @GetMapping("/Notice/Update/{id}")
    public String NoticeUpdate(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<BoardNoticeEntity> boardOpt = boardNoticeRepository.findById(id);
        if (boardOpt.isPresent()) {
            BoardNoticeEntity boardNoticeEntity = boardOpt.get();
            BoardNoticeDTO boardNoticeDTO = boardNoticeService.NoticeId(id);

            // 현재 로그인한 사용자의 아이디를 BoardTodayDTO에 설정
            Long userId = (Long) session.getAttribute("LoginId");
            if (!boardNoticeEntity.getUser().getId().equals(userId)) {
                return "redirect:/Board/Notice";
            }

            boardNoticeDTO.setUserId(userId);
            model.addAttribute("noticeUpdate", boardNoticeDTO);
        }

        return "admin/NoticeUpdate";
    }

    @PostMapping("/Notice/NoticeUpdate")
    public String UpdateNotice(Long id, @ModelAttribute BoardNoticeDTO boardNoticeDTO) {
        boardNoticeService.UpdateNotice(id, boardNoticeDTO);
        return "redirect:/Board/Notice";
    }

    @Transactional
    // 게시글 삭제
    @GetMapping("/Notice/Delete/{id}")
    public String Delete(@PathVariable Long id, HttpSession session) {

        // 로그인한 유저 id 값
        Long userId = (Long) session.getAttribute("LoginId");
        Optional<BoardNoticeEntity> boardOpt = boardNoticeRepository.findById(id);
        if (boardOpt.isPresent()) {
            BoardNoticeEntity boardNoticeEntity = boardOpt.get();
            // 현재 로그인한 사용자가 작성자가 아닌 경우에는 삭제 불가능
            if (!boardNoticeEntity.getUser().getId().equals(userId)) {
                return "redirect:/Board/Notice";
            }
            // 게시글 삭제
            boardNoticeService.Delete(id);
        }
        return "redirect:/Board/Notice";
    }

}