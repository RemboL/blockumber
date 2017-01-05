package pl.rembol.blockumber.stepdefs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class BlockDefinitionsService {

    private final List<Map<String, Object>> stepDefinitions = new ArrayList<>();

    private final List<Map<String, Object>> tagDefinitions = new ArrayList<>();

    private final List<Map<String, Object>> scenarioDefinitions = new ArrayList<>();

    private final String gluePath;

    @Autowired
    BlockDefinitionsService(@Value("${blockumber.glue:src/main/groovy}") String gluePath) {
        this.gluePath = gluePath;
    }

    @PostConstruct
    void setup() {
        List<StepDefinition> stepDefinitions = findStepDefinitions();
        StepDefConverter stepDefConverter = new StepDefConverter();

        stepDefinitions.stream()
                .map(StepDefinition::getPattern)
                .map(stepDefConverter::mapStepDefinitionPattern)
                .forEach(this.stepDefinitions::add);

        scenarioDefinitions.add(createFeatureDefinition());
        scenarioDefinitions.add(createBackgroundDefinition());
        scenarioDefinitions.add(createScenarioDefinition());

        tagDefinitions.add(createTagDefinition("Given", 240));
        tagDefinitions.add(createTagDefinition("When", 210));
        tagDefinitions.add(createTagDefinition("Then", 180));
        tagDefinitions.add(createTagDefinition("And", 150));
    }

    private List<StepDefinition> findStepDefinitions() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        RuntimeGlue glue = new RuntimeGlue(new UndefinedStepsTracker(), new LocalizedXStreams(classLoader));
        ResourceLoader resourceLoader = new MultiLoader(classLoader);
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        Reflections reflections = new Reflections(classFinder);
        Collection<? extends Backend> backends = reflections.instantiateSubclasses(Backend.class, "cucumber.runtime",
                new Class[]{ ResourceLoader.class }, new Object[]{ resourceLoader });
        for (Backend backend : backends) {
            backend.loadGlue(glue, Collections.singletonList(gluePath));
        }
        List<StepDefinition> stepDefinitions = new ArrayList<>();
        glue.reportStepDefinitions(stepDefinitions::add);
        return stepDefinitions;
    }

    public List<Map<String, Object>> getStepDefs() {
        return stepDefinitions;
    }

    public List<Map<String, Object>> getTagDefs() {
        return tagDefinitions;
    }

    public List<Map<String, Object>> getScenarioDefs() {
        return scenarioDefinitions;
    }

    private Map<String, Object> createFeatureDefinition() {
        Map<String, Object> featureDefinition = new HashMap<>();

        featureDefinition.put("message0", "Feature: %1");

        Map<String, String> nameDefinition = new HashMap<>();
        nameDefinition.put("type", "field_input");
        nameDefinition.put("name", "NAME");
        nameDefinition.put("check", "string");
        nameDefinition.put("text", "Name");
        Map<String, String> bodyDefinition = new HashMap<>();
        bodyDefinition.put("type", "input_statement");
        bodyDefinition.put("name", "BODY");

        featureDefinition.put("args0", Collections.singletonList(nameDefinition));
        featureDefinition.put("message1", "%1");
        featureDefinition.put("args1", Collections.singletonList(bodyDefinition));

        featureDefinition.put("colour", 32);

        return featureDefinition;
    }

    private Map<String, Object> createScenarioDefinition() {
        Map<String, Object> scenarioDefinition = new HashMap<>();

        scenarioDefinition.put("message0", "Scenario: %1");

        Map<String, String> nameDefinition = new HashMap<>();
        nameDefinition.put("type", "field_input");
        nameDefinition.put("name", "NAME");
        nameDefinition.put("check", "string");
        nameDefinition.put("text", "Name");
        Map<String, String> bodyDefinition = new HashMap<>();
        bodyDefinition.put("type", "input_statement");
        bodyDefinition.put("name", "BODY");

        scenarioDefinition.put("args0", Collections.singletonList(nameDefinition));
        scenarioDefinition.put("message1", "%1");
        scenarioDefinition.put("args1", Collections.singletonList(bodyDefinition));
        scenarioDefinition.put("nextStatement", "Action");
        scenarioDefinition.put("previousStatement", "Action");

        scenarioDefinition.put("colour", 64);

        return scenarioDefinition;
    }

    private Map<String, Object> createBackgroundDefinition() {
        Map<String, Object> scenarioDefinition = new HashMap<>();

        scenarioDefinition.put("message0", "Background:");

        Map<String, String> nameDefinition = new HashMap<>();
        nameDefinition.put("type", "field_label");
        nameDefinition.put("name", "NAME");
        nameDefinition.put("check", "string");
        nameDefinition.put("text", "");
        Map<String, String> bodyDefinition = new HashMap<>();
        bodyDefinition.put("type", "input_statement");
        bodyDefinition.put("name", "BODY");

        scenarioDefinition.put("args0", Collections.singletonList(nameDefinition));
        scenarioDefinition.put("message1", "%1");
        scenarioDefinition.put("args1", Collections.singletonList(bodyDefinition));
        scenarioDefinition.put("nextStatement", "Action");
        scenarioDefinition.put("previousStatement", "Action");

        scenarioDefinition.put("colour", 270);

        return scenarioDefinition;
    }

    private Map<String, Object> createTagDefinition(String name, int colour) {
        Map<String, Object> tagDefinition = new HashMap<>();

        tagDefinition.put("message0", name + " %1");
        tagDefinition.put("colour", colour);
        tagDefinition.put("nextStatement", "Action");
        tagDefinition.put("previousStatement", "Action");

        Map<String, String> bodyDefinition = new HashMap<>();
        bodyDefinition.put("type", "input_value");
        bodyDefinition.put("name", "BODY");

        tagDefinition.put("args0", Collections.singletonList(bodyDefinition));

        return tagDefinition;
    }
}
