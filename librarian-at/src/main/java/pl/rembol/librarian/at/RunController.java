package pl.rembol.librarian.at;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import cucumber.api.cli.Main;

@RestController
@RequestMapping("/run")
public class RunController {

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestBody String scenario) throws Throwable {
        scenario = java.net.URLDecoder.decode(scenario, "UTF-8");
        String feature = "Feature: test feature\n\n" + scenario;
        System.out.println(feature);
        Path scenarioDirectory = Files.createTempDirectory("blockumber_testDir_");

        Path scenarioFile = Files.createFile(scenarioDirectory.resolve("test.feature"));
        Files.write(scenarioFile, feature.getBytes());

        Path reportTestFile = Files.createTempFile("blockumber_testReport_", ".txt");
        System.out.println(reportTestFile.toAbsolutePath().toString());

        Main.run(new String[]{
                        "--plugin", "pretty:" + reportTestFile.toString(),
                        "--glue", "src/main/groovy",
                        scenarioDirectory.toString() },
                Thread.currentThread().getContextClassLoader());

        List<String> report = Files.readAllLines(reportTestFile);
        return prettify(StringUtils.join(report, "<br>"));
    }

    private String prettify(String report) {
        return report
                .replaceAll(" ", "&nbsp;")
                .replaceAll("\u001B\\[32m\u001B\\[1m", "<span style=\"color:#00ff55\">")
                .replaceAll("\u001B\\[32m", "<span style=\"color:#009933\">")
                .replaceAll("\u001B\\[31m\u001B\\[1m", "<span style=\"color:#ff0000\">")
                .replaceAll("\u001B\\[31m", "<span style=\"color:#990000\">")
                .replaceAll("\u001B\\[90m", "<span style=\"color:#999999\">")
                .replaceAll("\u001B\\[0m", "</span>");
    }
}
