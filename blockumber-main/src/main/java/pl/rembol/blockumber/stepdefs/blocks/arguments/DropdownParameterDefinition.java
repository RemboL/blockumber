package pl.rembol.blockumber.stepdefs.blocks.arguments;

import org.thymeleaf.util.StringUtils;
import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;
import pl.rembol.blockumber.stepdefs.blocks.WithOneLine;
import pl.rembol.blockumber.stepdefs.blocks.WithOutput;

import java.util.Collections;
import java.util.List;

public class DropdownParameterDefinition implements BlockDefinition, WithOneLine, WithOutput {

    private List<String> options;

    private DropdownValueDefinition valueDefinition;

    public DropdownParameterDefinition(List<String> options) {
        this.options = options;
        this.valueDefinition = new DropdownValueDefinition(options);
    }

    public String getName() {
        return "parameter.dropdown."+ StringUtils.join(options, ".");
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
        return Collections.singletonList(valueDefinition);
    }
}
