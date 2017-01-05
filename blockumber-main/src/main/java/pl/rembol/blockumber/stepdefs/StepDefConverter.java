package pl.rembol.blockumber.stepdefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class StepDefConverter {

    private static final Map<String, BiFunction<String, String, Map<String, Object>>> knownPatterns = new 
            LinkedHashMap<>();

    static {
        knownPatterns.put("\\(\\.\\*\\)", StepDefConverter::stringArgumentDefinition);
        knownPatterns.put("\\(\\\\d\\+\\)", StepDefConverter::integerArgumentDefinition);
        knownPatterns.put("\\([^\\|\\)]+(\\|[^\\|\\)]+)*\\)", StepDefConverter::dropdownArgumentDefinition);
    }

    Map<String, Object> mapStepDefinitionPattern(String sourcePattern) {
        Map<String, Object> blockDefinition = new HashMap<>();
        List<Map<String, Object>> argumentDefiitions = new ArrayList<>();
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
                    Map<String, Object> argumentDefinition = knownPatterns.get(argumentPattern).apply(matched,
                            "ARG" + argumentCount);
                    argumentDefiitions.add(argumentDefinition);
                    continue while_loop;
                }
            }
            break;
        }
        blockDefinition.put("message0", message);
        blockDefinition.put("colour", 125);
        blockDefinition.put("args0", argumentDefiitions);
        blockDefinition.put("output", "String");
        blockDefinition.put("sourceRegex", sourcePattern);
        return blockDefinition;
    }

    private static Map<String, Object> stringArgumentDefinition(String pattern, String argName) {
        Map<String, Object> argumentDefinition = new HashMap<>();
        argumentDefinition.put("type", "field_input");
        argumentDefinition.put("check", "String");
        argumentDefinition.put("text", "");
        argumentDefinition.put("name", argName);
        return argumentDefinition;
    }

    private static Map<String, Object> integerArgumentDefinition(String pattern, String argName) {
        Map<String, Object> argumentDefinition = new HashMap<>();
        argumentDefinition.put("type", "field_input");
        argumentDefinition.put("check", "Number");
        argumentDefinition.put("text", "");
        argumentDefinition.put("name", argName);
        return argumentDefinition;
    }

    private static Map<String, Object> dropdownArgumentDefinition(String pattern, String argName) {
        Map<String, Object> argumentDefinition = new HashMap<>();
        List<String> options = Arrays.asList(pattern.replaceAll("[\\(\\)]", "").split("\\|"));
        argumentDefinition.put("type", "field_dropdown");
        argumentDefinition.put("options", options.stream().map(option -> Arrays.asList(option, option)).collect(
                Collectors.toList()));
        argumentDefinition.put("name", argName);
        return argumentDefinition;
    }

}
