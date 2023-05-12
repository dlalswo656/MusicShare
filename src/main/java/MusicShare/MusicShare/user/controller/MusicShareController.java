package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.MusicShareDTO;
import MusicShare.MusicShare.user.entity.MusicShareEntity;
import MusicShare.MusicShare.user.entity.UserEntity;
import MusicShare.MusicShare.user.repository.MusicShareRepository;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.MusicShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        String url = "/Board/Today?page="; // 다음 페이지로 넘어가는 링크 URL

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


}
