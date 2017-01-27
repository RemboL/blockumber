package pl.rembol.blockumber.stepdefs.blocks;

import java.util.Collections;
import java.util.List;

public class ExamplesSetDefinition implements BlockDefinition, WithOneLine, WithPrevious, WithNext {

    // TODO check how to configure blocks
    // IDEA using cog icon you can set col/row size
    // IDEA2 searching for example placeholders in outline input proper types into example rows
    // https://github.com/google/blockly/blob/master/blocks/logic.js
    
    private final int colour;
    
    private int columns = 3;
    
    private int rows = 3;

    public ExamplesSetDefinition(int colour) {
        this.colour = colour;
    }

    @Override
    public String getMessage0() {
        return  "|%1|%2|%3|"; // TODO replace this
    }

    @Override
    public int getColour() {
        return colour;
    }

    @Override
    public String getJavascript() {
        return "return blockdef.message0.replace('%1', Blockly.JavaScript.valueToCode(block, 'BODY', Blockly.JavaScript.ORDER_NONE) || '')+ '\\n';"; // TODO replace this
    }

    @Override
    public String getName() {
        return "exampleSet";
    }

    @Override
    public List<InputDefinition> getArgs0() {
        return Collections.singletonList(new BodyInputValueDefinition());
    }

    @Override
    public String getPreviousStatement() {
        return "Action";
    }

    @Override
    public String getNextStatement() {
        return "Action";
    }
}
