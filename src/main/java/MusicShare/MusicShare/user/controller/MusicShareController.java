package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.MusicShareDTO;
import MusicShare.MusicShare.user.dto.TodayReplyDTO;
import MusicShare.MusicShare.user.entity.MusicShareEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.MusicShareRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.MusicShareService;
import MusicShare.MusicShare.user.service.TodayReplyService;
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
@RequestMapping("/Music")
public class MusicShareController {

    private final MusicShareRepository musicShareRepository;
    private final UserRepository userRepository;
    private final MusicShareService musicShareService;

    // 음악 공유
    @GetMapping("/Share")
    public String MusicShare(Model model, HttpSession session, @PageableDefault(size = 10) Pageable pageable) {

        // 페이징 처리
        Page<MusicShareDTO> musicShareDTOList = musicShareService.findAll(pageable);

        // 다음 페이지 넘어가는 링크
        int currentPage = musicShareDTOList.getNumber(); // 현재 페이지 번호
        int totalPages = musicShareDTOList.getTotalPages(); // 전체 페이지
        int prevPage = currentPage > 0 ? currentPage -1 : 0; // 이전 페이지
        int nextPage = currentPage + 1 < totalPages ? currentPage + 1 : totalPages -1; // 다음 페이지
        int startPage = currentPage / 10 * 10 + 1; // 시작 페이지
        int endPage = Math.min(startPage + 9, totalPages); // 끝 페이지

        String url = "/Music/Share?page="; // 다음 페이지로 넘어가는 링크 URL

        model.addAttribute("currentPage", currentPage); // 현재 페이지
        model.addAttribute("totalPages", totalPages); // 전체 페이지
        model.addAttribute("prevPageUrl", prevPage); // 이전 페이지
        model.addAttribute("nextPageUrl", nextPage); // 다음 페이지
        model.addAttribute("startPage", startPage); // 시작 페이지
        model.addAttribute("endPage", endPage); // 끝 페이지
        model.addAttribute("url", url);

        // DB 게시글 데이터 가져옴
        model.addAttribute("musicShareList", musicShareDTOList);

        return "music/Share";
    }

    // 게시글 작성
    @GetMapping("/ShareWrite")
    public String ShareWrite(MusicShareDTO musicShareDTO, HttpSession session) {

        // 비로그인 유저 글작성 Login
        Long LoginId = (Long) session.getAttribute("LoginId");
        if (LoginId == null) {
            return "redirect:/Login";
        }
        return "music/ShareWrite";
    }

    // 게시글 작성
    @PostMapping("/ShareWrite")
    public String ShareSave(@ModelAttribute MusicShareDTO musicShareDTO, HttpSession session) {
        String nonce = "";
        Long userId = (Long) session.getAttribute("LoginId"); // 로그인 한 유저
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        UserEntity user = userOpt.orElse(null);

        // 게시글 저장
        MusicShareEntity toShareEntity = musicShareService.toShareEntity(musicShareDTO, user);
        musicShareService.ShareSave(musicShareDTO, user);
        return "redirect:/Music/Share";
    }

    // 게시글 정보
    @GetMapping("/Share/{id}")
    public String ShareId(@PathVariable Long id, Model model, HttpSession session) {
        Long LoginId = (Long) session.getAttribute("LoginId");
        model.addAttribute("LoginId", LoginId);
        System.out.println("LoginId ?" + LoginId); // LoginId에 값이 잘 가져오는 지 디버깅

        String LoginName = (String) session.getAttribute("LoginName");
        model.addAttribute("LoginName", LoginName);
        System.out.println("고요" + LoginName); // LoginName에 name 값이 잘 가져오는 지 디버깅

        musicShareService.ShareHits(id);
        MusicShareDTO musicShareDTO = musicShareService.ShareId(id);
        model.addAttribute("boardShare", musicShareDTO); // boardTodayDTO에 값이 잘 가져오는 지 디버깅

//        // 댓글 리스트 가져오기
//        List<TodayReplyDTO> replyList = todayReplyService.getTodayByBoardTodayId(id);
//        model.addAttribute("replyList", replyList);
//
//        // 댓글 아이디
//        List<Long> replyIds = new ArrayList<>();
//        for(TodayReplyDTO reply : replyList){
//            replyIds.add(reply.getId());
//        }
//        model.addAttribute("replyIds", replyIds);

//        System.out.println("replyIds : " + replyIds);
//        System.out.println("replyList" + replyList); // 댓글 리스트를 잘 가져오는 지 디버깅
        System.out.println("musicShareDTO" + musicShareDTO); // 유저의 id boardTodayDTO로 잘 가져오는 지 테스트
        return "music/ShareDetail";
    }

}
