package pl.rembol.blockumber.stepdefs.blocks.scenarios;

import java.util.Collections;
import java.util.List;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.BodyInputStatementDefinition;
import pl.rembol.blockumber.stepdefs.blocks.ExamplesSetDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;
import pl.rembol.blockumber.stepdefs.blocks.WithNext;
import pl.rembol.blockumber.stepdefs.blocks.WithPrevious;
import pl.rembol.blockumber.stepdefs.blocks.WithThreeLines;
import pl.rembol.blockumber.stepdefs.blocks.arguments.ArgumentStatementDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.StringValueDefinition;

public class ScenarioOutlineDefinition implements BlockDefinition, WithThreeLines, WithPrevious, WithNext {
    @Override
    public String getMessage0() {
        return "Scenario Outline: %1";
    }

    @Override
    public String getMessage1() {
        return "%1";
    }

    @Override
    public String getName() {
        return "scenario.Scenario Outline";
    }

    @Override
    public List<InputDefinition> getArgs0() {
        return Collections.singletonList(new StringValueDefinition("NAME", "Name"));
    }

    @Override
    public List<InputDefinition> getArgs1() {
        return Collections.singletonList(new BodyInputStatementDefinition());
    }

    @Override
    public int getColour() {
        return 64;
    }

    @Override
    public String getJavascript() {
        return "return 'Scenario: '+ block.getFieldValue('NAME') + '\\n' + "+
                "(Blockly.JavaScript.statementToCode(block, 'BODY', Blockly.JavaScript.ORDER_NONE) || '')" +
                " + '\\n'" +
                " + 'Examples: \\n'" +
                " + (Blockly.JavaScript.statementToCode(block, 'EXAMPLES', Blockly.JavaScript.ORDER_NONE) || '');";
    }

    @Override
    public String getNextStatement() {
        return "Action";
    }

    @Override
    public String getPreviousStatement() {
        return "Action";
    }

    @Override
    public String getMessage2() {
        return "Examples: %1";
    }

    @Override
    public List<InputDefinition> getArgs2() {
        return Collections.singletonList(new ArgumentStatementDefinition("EXAMPLES", new ExamplesSetDefinition(16)));
    }
}
