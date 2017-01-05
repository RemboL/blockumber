package pl.rembol.blockumber.controllers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("value")
public class Value {
    
    @XStreamAsAttribute
    private String name = "BODY";

    private Block block;
    
    Value(Block block) {
        this.block = block;
    }

}
