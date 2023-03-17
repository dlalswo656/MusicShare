package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.UserDTO;
import MusicShare.MusicShare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    // 생성자 주입
    private final UserService userService;

    // 회원 리스트
    @GetMapping("/Admin/UserList")
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll();
        // 어떠한 html로 가져갈 데이터가 있으면 model 사용
        model.addAttribute("UserList", userDTOList);
        return "admin/UserList";
    }

    // 회원 삭제
    @GetMapping("/User/Delete/{id}")
    public String deleteById(@PathVariable Long id, HttpSession session) {
        Long UserId = (Long) session.getAttribute("LoginId"); // pk 값이 Long 이므로 String이 아닌 Long으로 해야함
        String loginRole = (String)session.getAttribute("LoginRole");//세션에서 역할 값 가져오기

        if ("Admin".equals(loginRole)) { // 사용자가 admin이라면
            if (UserId.equals(id) ) { // 세션에 유지되고 있는 사용자의 pk아이디가 삭제하려는 id값과 같으면
                userService.deleteById(id);
                session.invalidate();
                return "redirect:/";
            }
            userService.deleteById(id); // (삭제할)아이디를 삭제
            return "redirect:/Admin/UserList";
        } else if ("User".equals(loginRole)) { // 사용자가 user라면
            userService.deleteById(id); // (삭제할)아이디를 삭제
            session.invalidate(); // 세션무효화
            return "redirect:/";   // index페이지 리턴
        } else {

            System.out.println("예상외 예외발생");
            return "redirect:/";
        }
    }
}