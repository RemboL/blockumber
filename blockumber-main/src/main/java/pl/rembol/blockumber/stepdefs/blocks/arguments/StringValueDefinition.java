package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.ValueDefinition;

public class StringValueDefinition implements ValueDefinition {

    public static final StringValueDefinition INSTANCE = new StringValueDefinition();

    private String type = "field_input";
    private String name = "VALUE";
    private String check = "String";
    private String text = "";

    public StringValueDefinition() {
    }

    public StringValueDefinition(String name, String text) {
        this.name = name;
        this.text = text;
    }

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
