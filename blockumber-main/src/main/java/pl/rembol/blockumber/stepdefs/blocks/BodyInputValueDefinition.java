package pl.rembol.blockumber.stepdefs.blocks;

public class BodyInputValueDefinition implements InputDefinition {


    @Override
    public String getType() {
        return "input_value";
    }

    @Override
    public String getName() {
        return "BODY";
    }
}
