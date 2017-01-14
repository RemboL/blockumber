package pl.rembol.blockumber.stepdefs.blocks;

public class BodyInputStatementDefinition implements InputDefinition {


    @Override
    public String getType() {
        return "input_statement";
    }

    @Override
    public String getName() {
        return "BODY";
    }
}
