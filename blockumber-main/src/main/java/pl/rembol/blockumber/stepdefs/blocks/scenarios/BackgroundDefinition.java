package pl.rembol.blockumber.stepdefs.blocks.scenarios;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.BodyInputStatementDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;
import pl.rembol.blockumber.stepdefs.blocks.WithNext;
import pl.rembol.blockumber.stepdefs.blocks.WithPrevious;
import pl.rembol.blockumber.stepdefs.blocks.WithTwoLines;

import java.util.Collections;
import java.util.List;

public class BackgroundDefinition implements BlockDefinition, WithTwoLines, WithPrevious, WithNext {
    @Override
    public String getMessage0() {
        return "Background:";
    }

    @Override
    public String getMessage1() {
        return "%1";
    }

    @Override
    public String getName() {
        return "scenario.Background";
    }

    @Override
    public List<InputDefinition> getArgs0() {
        return Collections.emptyList();
    }

    @Override
    public List<InputDefinition> getArgs1() {
        return Collections.singletonList(new BodyInputStatementDefinition());
    }

    @Override
    public int getColour() {
        return 270;
    }

    @Override
    public String getJavascript() {
        return "return 'Background: \\n' + "+
                "(Blockly.JavaScript.statementToCode(block, 'BODY', Blockly.JavaScript.ORDER_NONE) || '')" +
                " + '\\n';";
    }

    @Override
    public String getNextStatement() {
        return "Action";
    }

    @Override
    public String getPreviousStatement() {
        return "Action";
    }
}
