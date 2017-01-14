package pl.rembol.blockumber.stepdefs.blocks;

import pl.rembol.blockumber.stepdefs.blocks.arguments.ArgumentDefinition;

import java.util.List;

public class StepBlockDefinition implements BlockDefinition, WithOutput, WithOneLine {

    private String message;

    private String sourceRegex;

    private List<ArgumentDefinition> argumentDefinitions;

    public StepBlockDefinition(String message, String sourceRegex, List<ArgumentDefinition> argumentDefinitions) {
        this.message = message;
        this.sourceRegex = sourceRegex;
        this.argumentDefinitions = argumentDefinitions;
    }

    @Override
    public String getMessage0() {
        return message;
    }

    @Override
    public List<? extends InputDefinition> getArgs0() {
        return argumentDefinitions;
    }

    @Override
    public String getOutput() {
        return "String";
    }

    @Override
    public String getName() {
        return "step."+sourceRegex;
    }

    @Override
    public int getColour() {
        return 125;
    }

    @Override
    public String getJavascript() {
        return " var text = blockdef.message0;" +
                " for (var i = 1; i <= blockdef.args0.length; i++) {" +
                " text = text.replace('%' + i, Blockly.JavaScript.valueToCode(block, 'ARG' + i, Blockly.JavaScript.ORDER_ADDITION));" +
                " }" +
                " return [text, Blockly.JavaScript.ORDER_MEMBER];";
    }

    public boolean getInputsInline() {
        return true;
    }

    public String getSourceRegex() {
        return sourceRegex;
    }

    public List<ArgumentDefinition> getArgumentDefinitions() {
        return argumentDefinitions;
    }
}
