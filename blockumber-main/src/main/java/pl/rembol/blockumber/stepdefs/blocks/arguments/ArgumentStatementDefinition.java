package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.InputDefinition;

public class ArgumentStatementDefinition implements InputDefinition {

    private String name;

    private BlockDefinition type;

    public ArgumentStatementDefinition(String name, BlockDefinition type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return "input_statement";
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
