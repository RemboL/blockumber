package pl.rembol.blockumber.stepdefs;

import org.springframework.stereotype.Component;
import pl.rembol.blockumber.stepdefs.blocks.BlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.StepBlockDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.ArgumentDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.DecimalParameterDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.DropdownParameterDefinition;
import pl.rembol.blockumber.stepdefs.blocks.arguments.StringParameterDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StepDefConverter {

    private final Map<String, BiFunction<String, String, ArgumentDefinition>> knownPatterns = new
            LinkedHashMap<>();

    StepDefConverter() {
        knownPatterns.put("\\(\\.\\*\\)", StepDefConverter::stringArgumentDefinition);
        knownPatterns.put("\\(\\\\d\\+\\)", StepDefConverter::decimalArgumentDefinition);
        knownPatterns.put("\\(\\\\d\\+\\.\\?\\\\d\\*\\)", StepDefConverter::decimalArgumentDefinition);
        knownPatterns.put("\\([^\\|\\)]+(\\|[^\\|\\)]+)*\\)", StepDefConverter::dropdownArgumentDefinition);
        knownPatterns.put("\\([^\\|\\)]+(\\|[^\\|\\)]+)*\\)\\?", StepDefConverter::dropdownWithEmptyOptionArgumentDefinition);
    }

    StepBlockDefinition mapStepDefinitionPattern(String sourcePattern, Map<String, BlockDefinition> parameterDefinitions) {
        List<ArgumentDefinition> argumentDefinitions = new ArrayList<>();
        String message = sourcePattern;

        if (message.startsWith("^")) {
            message = message.replace("^", "");
        }
        if (message.endsWith("$") && !message.endsWith("\\$")) {
            message = message.substring(0, message.length() - 1);
        }
        int argumentIndex;
        int argumentCount = 0;
        while_loop:
        while ((argumentIndex = message.indexOf("(")) != -1) {
            for (String argumentPattern : knownPatterns.keySet()) {
                Matcher matcher = Pattern.compile(argumentPattern).matcher(message.substring(argumentIndex));
                if (matcher.find() && matcher.start() == 0) {
                    String matched = matcher.group();
                    argumentCount++;
                    message = message.replaceFirst(argumentPattern, "%" + argumentCount);
                    ArgumentDefinition argumentDefinition = knownPatterns.get(argumentPattern).apply(matched,
                            "ARG" + argumentCount);
                    parameterDefinitions.put(argumentDefinition.getBlockType().getName(), argumentDefinition.getBlockType());
                    argumentDefinitions.add(argumentDefinition);
                    continue while_loop;
                }
            }
            break;
        }
        return new StepBlockDefinition(message, sourcePattern, argumentDefinitions);
    }



    private static ArgumentDefinition stringArgumentDefinition(String pattern, String argName) {
        return new ArgumentDefinition(argName, StringParameterDefinition.INSTANCE);
    }

    private static ArgumentDefinition decimalArgumentDefinition(String pattern, String argName) {
        return new ArgumentDefinition(argName, DecimalParameterDefinition.INSTANCE);
    }

    private static ArgumentDefinition dropdownArgumentDefinition(String pattern, String argName) {
        List<String> options = Arrays.asList(pattern.replaceAll("[\\(\\)]", "").split("\\|"));
        DropdownParameterDefinition parameterDefinition = new DropdownParameterDefinition(options);
        return new ArgumentDefinition(argName, parameterDefinition);

    }

    private static ArgumentDefinition dropdownWithEmptyOptionArgumentDefinition(String pattern, String argName) {
        List<String> options = Arrays.asList(pattern.replace("\\(", "").replace("\\)\\?", "").split("\\|"));
        options.add("");
        DropdownParameterDefinition parameterDefinition = new DropdownParameterDefinition(options);
        return new ArgumentDefinition(argName, parameterDefinition);
    }
}
