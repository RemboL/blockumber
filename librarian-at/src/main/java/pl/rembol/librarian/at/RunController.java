package pl.rembol.librarian.at;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import cucumber.api.cli.Main;

@Controller
@RequestMapping("/run")
public class RunController {

    @RequestMapping
    public String get(ModelMap model) throws Throwable {

        Path reportTestFile = Files.createTempFile("testRun", ".txt");
        System.out.println(reportTestFile.toAbsolutePath().toString());

        Main.run(new String[]{
                        "--plugin", "pretty:" + reportTestFile.toAbsolutePath().toString(),
                        "--glue", "src/main/groovy",
                        "src/test/resources"},
                Thread.currentThread().getContextClassLoader());

        List<String> report = Files.readAllLines(reportTestFile);
        model.addAttribute("report", StringUtils.join(report, "<br>"));
        return "report";
    }

}
