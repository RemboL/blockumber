package pl.rembol.librarian.at;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String get() {
        return "index";
    }
}
