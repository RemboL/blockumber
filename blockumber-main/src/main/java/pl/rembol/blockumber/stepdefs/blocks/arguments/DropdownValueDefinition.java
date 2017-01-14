package pl.rembol.blockumber.stepdefs.blocks.arguments;

import pl.rembol.blockumber.stepdefs.blocks.ValueDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DropdownValueDefinition implements ValueDefinition {

    private String type = "field_dropdown";
    private String name = "VALUE";
    private String check = "";
    private String text = "";
    private List<List<String>> options;

    public DropdownValueDefinition(List<String> options) {
        this.options =  options.stream().map(option -> Arrays.asList(option, option)).collect(
                Collectors.toList());
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

    public List<List<String>> getOptions() {
        return options;
    }
}
