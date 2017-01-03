package pl.rembol.blockumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blockumber")
class IndexController {

    @Autowired
    private StepDefsService stepDefsService;

    @RequestMapping
    String get(ModelMap model) {
        model.addAttribute("stepdefs", stepDefsService.getStepDefs());
        model.addAttribute("tagdefs", stepDefsService.getTagDefs());
        model.addAttribute("scenariodefs", stepDefsService.getScenarioDefs());

        return "index";
    }
}
