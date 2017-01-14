package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;
import pl.rembol.blockumber.stepdefs.blocks.WithOneLine;
import pl.rembol.blockumber.stepdefs.blocks.WithOutput;

import java.util.Collections;
import java.util.List;

public class DecimalParameterDefinition implements BlockDefinition, WithOneLine, WithOutput {
    public static final DecimalParameterDefinition INSTANCE = new DecimalParameterDefinition();

    public String getName() {
        return "parameter.decimal";
    }

    public int getColour() {
        return 320;
    }

    public String getOutput() {
        return "String";
    }

    public String getMessage0() {
        return "%1";
    }

    public String getJavascript() {
        return "return [block.getFieldValue('VALUE'), Blockly.JavaScript.ORDER_MEMBER];";
    }

    public List<InputDefinition> getArgs0() {
        return Collections.singletonList(DecimalValueDefinition.INSTANCE);
    }
}
