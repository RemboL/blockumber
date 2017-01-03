package pl.rembol.blockumber;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import cucumber.api.cli.Main;

@RestController
@RequestMapping("/blockumber/run")
class RunController {

    @Autowired
    private RunService runService;

    @RequestMapping(method = RequestMethod.POST)
    String post(@RequestBody String feature) throws Throwable {
        feature = java.net.URLDecoder.decode(feature, "UTF-8");
        if (!feature.startsWith("Feature")) {
            feature = "Feature: test feature\n\n" + feature;
        }

        List<String> report = runService.run(feature);
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
