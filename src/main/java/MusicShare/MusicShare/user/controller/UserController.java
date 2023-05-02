package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.UserDTO;
import MusicShare.MusicShare.user.repository.UserRepository;
import MusicShare.MusicShare.user.service.EmailService;
import MusicShare.MusicShare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Repository
public class UserController {

    // 생성자 주입
    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    // Home
    @GetMapping("/index")
    public String Home() {
        return "index";
    }

    // 회원가입 페이지
    @GetMapping("/Join")
    public String SaveForm() {
        System.out.println("연결성공");
        return "user/Join";
    }

    // 회원가입 이메일 중복 체크
    @PostMapping("/User/email-check")
    public @ResponseBody String emailCheck(@RequestParam("email") String email) {
        System.out.println("userEmail = " + email);
        String checkResult = userService.emailCheck(email);
        return checkResult;
    }

    @PostMapping("/Login")
    public String Save(@ModelAttribute UserDTO userDTO, Model model) {
        // 회원가입 할 때 Role 값이 null 이면 User로 변환
        if (userDTO.getRole() == null) {
            userDTO.setRole("User");
        }
        userService.Save(userDTO);
        return "user/Login";
    }

    @GetMapping("/Login")
    public String LoginForm() {

        return "user/Login";
    }

    // 로그인
    @PostMapping("/User/Login")
    public String Login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO LoginResult = userService.Login(userDTO);
        if(LoginResult != null) {
            // Login 성공 session 저장
            session.setAttribute("LoginId", LoginResult.getId()); // pk 값 불러오기
            session.setAttribute("LoginUser", LoginResult); // 로그인 후에 유저의 id를 보이기 위해 추가
            session.setAttribute("LoginEmail", LoginResult.getEmail());
            session.setAttribute("LoginName", LoginResult.getName());
            session.setAttribute("LoginRole", LoginResult.getRole()); // 사용자의 역할 정보를 추가로 저장
            System.out.println("userId : " + LoginResult.getId());
           return "index";

        } else {
            // Login 실패
            return "user/Login";
        }
    }

    // 마이페이지
    @GetMapping("/User/{id}")
    public String findById(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("User", userDTO);
        return "user/MyPage";
    }

    // 내정보 수정
    @GetMapping("/User/Update")
    public String UpdateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("LoginEmail");
        UserDTO userDTO = userService.UpdateForm(myEmail);
        model.addAttribute("UpdateUser", userDTO);
        return "user/Update";

    }

    @PostMapping("/User/Update")
    public String Update(@ModelAttribute UserDTO userDTO) {
        userService.Update(userDTO);
        return "redirect:/User/" + userDTO.getId();
    }

    // 비밀번호 찾기 뷰 페이지
    @GetMapping("/User/sendPw")
    public String sendPw() {

        return "user/ResetPw";
    }

    // 로그아웃
    @GetMapping("/Logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 정보 삭제
        return "redirect:/"; // 메인 페이지로 리다이렉트
    }

}