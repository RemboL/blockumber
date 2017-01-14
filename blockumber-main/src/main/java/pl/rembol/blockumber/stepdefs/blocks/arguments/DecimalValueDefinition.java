package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.ValueDefinition;

public class DecimalValueDefinition implements ValueDefinition {

    public static final DecimalValueDefinition INSTANCE = new DecimalValueDefinition();

    private String type = "field_input";
    private String name = "VALUE";
    private String check = "Number";
    private String text = "";

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCheck() {
        return check;
    }

    public String getText() {
        return text;
    }
}
