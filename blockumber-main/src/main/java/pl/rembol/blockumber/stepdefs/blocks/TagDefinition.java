package pl.rembol.blockumber.stepdefs.blocks;

import java.util.Collections;
import java.util.List;

public class TagDefinition implements BlockDefinition, WithOneLine, WithPrevious, WithNext {

    private final String name;

    private final int colour;

    public TagDefinition(String name, int colour) {
        this.name = name;
        this.colour = colour;
    }

    @Override
    public String getMessage0() {
        return name + " %1";
    }

    @Override
    public int getColour() {
        return colour;
    }

    @Override
    public String getJavascript() {
        return "return blockdef.message0.replace('%1', Blockly.JavaScript.valueToCode(block, 'BODY', Blockly.JavaScript.ORDER_NONE) || '')+ '\\n';";
    }

    @Override
    public String getName() {
        return "tag."+name;
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
