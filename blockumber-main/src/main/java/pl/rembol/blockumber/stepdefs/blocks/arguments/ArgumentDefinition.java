package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;

public class ArgumentDefinition implements InputDefinition {

    private String name;

    private BlockDefinition type;

    public ArgumentDefinition(String name, BlockDefinition type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return "input_value";
    }

    public String getText() {
        return "";
    }

    public String getName() {
        return name;
    }

    public BlockDefinition getBlockType() {
        return type;
    }

}
