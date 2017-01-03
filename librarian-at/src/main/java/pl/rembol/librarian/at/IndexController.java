package pl.rembol.librarian.at;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private StepDefsService stepDefsService;

    @RequestMapping
    public String get(ModelMap model) {
        model.addAttribute("stepdefs", stepDefsService.get());

        return "index";
    }
}
