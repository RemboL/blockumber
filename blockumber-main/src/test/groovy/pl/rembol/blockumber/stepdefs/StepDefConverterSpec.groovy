package pl.rembol.blockumber.stepdefs

import spock.lang.Specification

class StepDefConverterSpec extends Specification {

    StepDefConverter stepDefConverter = new StepDefConverter()

    def 'non-parametrized stepdef'() {
        given:
            String pattern = 'simple stepdef'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'simple stepdef'
            block['args0'] == []
    }

    def 'single string parameter'() {
        given:
            String pattern = 'stepdef with (.*) parameter'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'stepdef with %1 parameter'
            block['args0'].size() == 1
            block['args0'][0]['check'] == 'String'
    }

    def 'single number parameter'() {
        given:
            String pattern = 'stepdef with (\\d+) parameter'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'stepdef with %1 parameter'
            block['args0'].size() == 1
            block['args0'][0]['check'] == 'Number'
    }

    def 'single options parameter'() {
        given:
            String pattern = 'the question = (to be|not to be)'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'the question = %1'
            block['args0'].size() == 1
            block['args0'][0]['type'] == 'field_dropdown'
            block['args0'][0]['options'] == [['to be', 'to be'], ['not to be', 'not to be']]
    }

    def 'multiple parameters'() {
        given:
            String pattern = 'very (.*) complicated (.*) step (.*) definition'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'very %1 complicated %2 step %3 definition'
            block['args0'].size() == 3
    }

    def 'limited regex'() {
        given:
            String pattern = '^stepdef$'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'stepdef'
    }

    def 'ending with real dollar'() {
        given:
            String pattern = '^stepdef\\$'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'stepdef\\$'
    }

    def 'ending with real dollar and limit'() {
        given:
            String pattern = '^stepdef\\$$'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'stepdef\\$'
    }

    def 'unrecognized regex'() {
        given:
            String pattern = 'unrecognized pattern (||)'
        when:
            Map<String, Object> block = stepDefConverter.mapStepDefinitionPattern(pattern)
        then:
            block['message0'] == 'unrecognized pattern (||)'
    }
}
