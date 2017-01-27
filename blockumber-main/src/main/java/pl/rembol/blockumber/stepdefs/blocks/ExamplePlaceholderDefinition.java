package pl.rembol.blockumber.stepdefs.blocks;

import java.util.Collections;
import java.util.List;

import pl.rembol.blockumber.stepdefs.blocks.arguments.StringValueDefinition;

public class ExamplePlaceholderDefinition implements BlockDefinition, WithOneLine, WithOutput {
    public static final ExamplePlaceholderDefinition INSTANCE = new ExamplePlaceholderDefinition();

    public String getName() {
        return "examples.placeholder";
    }

    public int getColour() {
        return 16;
    }

    public String getOutput() {
        return "String";
    }

    public String getMessage0() {
        return "<%1>";
    }

    public String getJavascript() {
        return "return ['<' + block.getFieldValue('VALUE') + '>', Blockly.JavaScript.ORDER_MEMBER];";
    }

    public List<InputDefinition> getArgs0() {
        return Collections.singletonList(StringValueDefinition.INSTANCE);
    }
}
