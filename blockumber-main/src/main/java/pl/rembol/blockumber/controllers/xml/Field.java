package pl.rembol.blockumber.controllers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("field")
@XStreamConverter(value= ToAttributedValueConverter.class, strings = {"value"})
class Field {
    private String name;

    private String value;
    
    Field(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
