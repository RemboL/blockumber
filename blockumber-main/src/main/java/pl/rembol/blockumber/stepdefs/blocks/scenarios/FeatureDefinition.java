package pl.rembol.blockumber.stepdefs.blocks.scenarios;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.BodyInputStatementDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.StringValueDefinition;
import pl.rembol.blockumber.stepdefs.blocks.WithTwoLines;

import java.util.Collections;
import java.util.List;

public class FeatureDefinition implements BlockDefinition, WithTwoLines {
    @Override
    public String getMessage0() {
        return "Feature: %1";
    }

    @Override
    public String getMessage1() {
        return "%1";
    }

    @Override
    public String getName() {
        return "scenario.Feature";
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
        return 32;
    }

    @Override
    public String getJavascript() {
        return "return 'Feature: '+ block.getFieldValue('NAME') + '\\n' + "+
                "(Blockly.JavaScript.statementToCode(block, 'BODY', Blockly.JavaScript.ORDER_NONE) || '')" +
                " + '\\n';";
    }
}
