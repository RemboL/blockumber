package pl.rembol.blockumber;

import cucumber.api.cli.Main;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
class RunService {

    @Value("${blockumber.glue:src/main/groovy}")
    private String gluePath;

    List<String> run(String feature) throws IOException {
        Path featureDirectory = Files.createTempDirectory("blockumber_testDir_");

        Path featureFile = Files.createFile(featureDirectory.resolve("test.feature"));
        Files.write(featureFile, feature.getBytes());

        Path reportTestFile = Files.createTempFile("blockumber_testReport_", ".txt");

        Main.run(new String[]{
                        "--plugin", "pretty:" + reportTestFile.toString(),
                        "--glue", gluePath,
                        featureDirectory.toString()},
                Thread.currentThread().getContextClassLoader());

        return Files.readAllLines(reportTestFile);
    }

}
