package pl.rembol.blockumber.controllers.xml;

import java.util.List;
import java.util.Map;

import gherkin.formatter.Formatter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

class BlockBuilder implements Formatter {

    private final List<Map<String, Object>> stepDefinitions;

    private final Xml xml;

    private Block currentCucumberFeature;

    private Block currentScenario;

    BlockBuilder(List<Map<String, Object>> stepDefinitions, Xml xml) {
        this.stepDefinitions = stepDefinitions;
        this.xml = xml;
    }

    @Override
    public void uri(String uri) {
    }

    @Override
    public void feature(Feature feature) {
        currentCucumberFeature = new Block("Feature: %1");
        currentCucumberFeature.setPosition(100, 50);
        currentCucumberFeature.addField(new Field("NAME", feature.getName()));

        xml.addBlock(currentCucumberFeature);
    }

    @Override
    public void background(Background background) {
        currentScenario = new Block("Background:");
        currentCucumberFeature.addToStatement(currentScenario);
    }

    @Override
    public void scenario(Scenario scenario) {
        currentScenario = new Block("Scenario: %1");
        currentScenario.addField(new Field("NAME", scenario.getName()));
        currentCucumberFeature.addToStatement(currentScenario);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void examples(Examples examples) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void step(Step step) {
        Block keywordBlock = new Block(step.getKeyword() + "%1");
        keywordBlock.addValueWithBlock(stepDefinitions
                .stream()
                .filter(stepDef -> step.getName().matches(stepDef.get("sourceRegex").toString()))
                .findFirst()
                .map(stepDefinition -> mapStepDefinition(stepDefinition, step.getName()))
                .orElse(new Block(step.getName())));

        currentScenario.addToStatement(keywordBlock);
    }

    private Block mapStepDefinition(Map<String, Object> stepDefinition, String step) {
        Block block = new Block(stepDefinition.get("message0").toString());
        List<Map<String, Object>> argDefinitions = (List<Map<String, Object>>) stepDefinition.get("args0");
        argDefinitions.stream().map(argDef -> argDef.get("name")).map(Object::toString).forEach(argName -> {
            Integer count = new Integer(argName.replace("ARG", ""));
            block.addField(new Field(argName,
                    step.replaceFirst(stepDefinition.get("sourceRegex").toString(), "$" + count)));
        });
        return block;
    }

    @Override
    public void eof() {
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
    }

    @Override
    public void done() {
    }

    @Override
    public void close() {
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        // NoOp
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        // NoOp
    }

}
