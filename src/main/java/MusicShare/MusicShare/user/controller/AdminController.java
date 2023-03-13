package MusicShare.MusicShare.user.controller;

import MusicShare.MusicShare.user.dto.UserDTO;
import MusicShare.MusicShare.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/Admin/UserList"; // model의 데이터를 담아서 가기 때문에 redirect 사용 redirect 뒤에는 매핑 주소
    }

}
