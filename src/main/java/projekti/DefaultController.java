package projekti;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("*")
    public String welcome() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
