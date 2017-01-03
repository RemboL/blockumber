package pl.rembol.librarian.at;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import cucumber.runtime.Backend;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Reflections;
import cucumber.runtime.RuntimeGlue;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.UndefinedStepsTracker;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.xstream.LocalizedXStreams;

@Service
public class StepDefsService {

    private static final Map<String, String> knownPatterns = new HashMap<>();

    static {
        knownPatterns.put("(.*)", "String");
    }

    private final List<Map<String, Object>> blockDefinitions = new ArrayList<>();

    @PostConstruct
    public void findStepDefs() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        RuntimeGlue glue = new RuntimeGlue(new UndefinedStepsTracker(), new LocalizedXStreams(classLoader));
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        Reflections reflections = new Reflections(classFinder);
        Collection<? extends Backend> backends = reflections.instantiateSubclasses(Backend.class, "cucumber.runtime",
                new Class[]{ ResourceLoader.class }, new Object[]{ resourceLoader });
        for (Backend backend : backends) {
            backend.loadGlue(glue, Collections.singletonList("src/main/groovy"));
        }
        List<StepDefinition> stepDefinitions = new ArrayList<>();
        glue.reportStepDefinitions(stepDefinitions::add);
        stepDefinitions.stream().map(this::mapStepDefinition).forEach(blockDefinitions::add);
    }

    public List<Map<String, Object>> get() {
        return blockDefinitions;
    }

    private Map<String, Object> mapStepDefinition(StepDefinition stepDefinition) {
        Map<String, Object> blockDefinition = new HashMap<>();
        List<Map<String, String>> argumentDefiitions = new ArrayList<>();

        String pattern = stepDefinition.getPattern();
        int argumentIndex;
        int argumentCount = 0;
        while ((argumentIndex = pattern.indexOf("(")) != -1) {
            for (String argumentPattern : knownPatterns.keySet()) {
                if (pattern.substring(argumentIndex).startsWith(argumentPattern)) {
                    argumentCount++;
                    pattern = pattern.replace(argumentPattern, "%" + argumentCount);
                    Map<String, String> argumentDefinition = new HashMap<>();
                    argumentDefinition.put("type", "field_input");
                    argumentDefinition.put("name", "arg" + argumentCount);
                    argumentDefinition.put("check", knownPatterns.get(argumentPattern));
                    argumentDefinition.put("text", "");
                    argumentDefiitions.add(argumentDefinition);
                }
            }
        }
        blockDefinition.put("message0", pattern);
        blockDefinition.put("colour", 125);
        blockDefinition.put("args0", argumentDefiitions);
        blockDefinition.put("nextStatement", "Action");
        blockDefinition.put("previousStatement", "Action");
        return blockDefinition;
    }
}
