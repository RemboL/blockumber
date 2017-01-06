package pl.rembol.blockumber.controllers.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.rembol.blockumber.stepdefs.BlockDefinitionsService;

@Controller
@RequestMapping("/blockumber")
class IndexController {

    @Autowired
    private BlockDefinitionsService stepDefsService;

    @RequestMapping
    String get(ModelMap model) {
        model.addAttribute("stepdefs", stepDefsService.getStepDefs());
        model.addAttribute("keyworddefs", stepDefsService.getKeywordDefs());
        model.addAttribute("scenariodefs", stepDefsService.getScenarioDefs());

        return "index";
    }
}
