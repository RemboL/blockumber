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
import pl.rembol.blockumber.stepdefs.blocks.ExamplePlaceholderDefinition;
import pl.rembol.blockumber.stepdefs.blocks.StepBlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.scenarios.BackgroundDefinition;
import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.scenarios.FeatureDefinition;
import pl.rembol.blockumber.stepdefs.blocks.scenarios.ScenarioDefinition;
import pl.rembol.blockumber.stepdefs.blocks.TagDefinition;
import pl.rembol.blockumber.stepdefs.blocks.scenarios.ScenarioOutlineDefinition;

@Service
public class BlockDefinitionsService {

    private final Map<String, BlockDefinition> parameterDefinitions = new HashMap<>();

    private final List<StepBlockDefinition> stepDefinitions = new ArrayList<>();

    private final List<TagDefinition> keywordsDefinitions = new ArrayList<>();

    private final List<BlockDefinition> scenarioDefinitions = new ArrayList<>();

    private final String gluePath;

    private final StepDefConverter stepDefConverter;

    @Autowired
    BlockDefinitionsService(@Value("${blockumber.glue:src/main/groovy}") String gluePath, StepDefConverter stepDefConverter) {
        this.gluePath = gluePath;
        this.stepDefConverter = stepDefConverter;
    }

    @PostConstruct
    void setup() {
        List<StepDefinition> stepDefinitions = findStepDefinitions();

        stepDefinitions.stream()
                .map(StepDefinition::getPattern)
                .map(pattern -> stepDefConverter.mapStepDefinitionPattern(pattern, parameterDefinitions))
                .forEach(this.stepDefinitions::add);
        scenarioDefinitions.add(new FeatureDefinition());
        scenarioDefinitions.add(new BackgroundDefinition());
        scenarioDefinitions.add(new ScenarioDefinition());
        scenarioDefinitions.add(new ScenarioOutlineDefinition());
        scenarioDefinitions.add(ExamplePlaceholderDefinition.INSTANCE);

        keywordsDefinitions.add(new TagDefinition("Given", 240));
        keywordsDefinitions.add(new TagDefinition("When", 210));
        keywordsDefinitions.add(new TagDefinition("Then", 180));
        keywordsDefinitions.add(new TagDefinition("And", 150));
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

    public Collection<BlockDefinition> getParameterDefs() {
        return parameterDefinitions.values();
    }

    public List<StepBlockDefinition> getStepDefs() {
        return stepDefinitions;
    }

    public List<TagDefinition> getKeywordDefs() {
        return keywordsDefinitions;
    }

    public List<BlockDefinition> getScenarioDefs() {
        return scenarioDefinitions;
    }

}
