package pl.rembol.blockumber.controllers.xml;

import gherkin.formatter.Formatter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import pl.rembol.blockumber.stepdefs.blocks.StepBlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.ArgumentDefinition;

import java.util.List;

class BlockBuilder implements Formatter {

    private final List<StepBlockDefinition> stepDefinitions;

    private final Xml xml;

    private Block currentCucumberFeature;

    private Block currentScenario;

    BlockBuilder(List<StepBlockDefinition> stepDefinitions, Xml xml) {
        this.stepDefinitions = stepDefinitions;
        this.xml = xml;
    }

    @Override
    public void uri(String uri) {
    }

    @Override
    public void feature(Feature feature) {
        currentCucumberFeature = new Block("scenario.Feature");
        currentCucumberFeature.setPosition(100, 50);
        currentCucumberFeature.addField(new Field("NAME", feature.getName()));

        xml.addBlock(currentCucumberFeature);
    }

    @Override
    public void background(Background background) {
        currentScenario = new Block("scenario.Background");
        currentCucumberFeature.addToStatement(currentScenario);
    }

    @Override
    public void scenario(Scenario scenario) {
        currentScenario = new Block("scenario.Scenario");
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
        Block keywordBlock = new Block("tag." + step.getKeyword().replaceAll(" ", ""));
        keywordBlock.addValueWithBlock(stepDefinitions
                .stream()
                .filter(stepDef -> step.getName().matches(stepDef.getSourceRegex()))
                .findFirst()
                .map(stepDefinition -> mapStepDefinition(stepDefinition, step.getName()))
                .orElse(new Block(step.getName())));

        currentScenario.addToStatement(keywordBlock);
    }

    private Block mapStepDefinition(StepBlockDefinition stepDefinition, String step) {
        Block block = new Block("step." + stepDefinition.getSourceRegex());
        List<ArgumentDefinition> argDefinitions = stepDefinition.getArgumentDefinitions();
        argDefinitions.stream().forEach(argDef -> {
            Integer count = new Integer(argDef.getName().replace("ARG", ""));
            Block parameterBlock = new Block(argDef.getBlockType().getName());
            parameterBlock.addField(new Field("VALUE", step.replaceFirst(stepDefinition.getSourceRegex(), "$" + count)));
            block.addValueWithBlock(argDef.getName(), parameterBlock);
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
