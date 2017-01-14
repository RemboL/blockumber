package pl.rembol.blockumber.controllers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("value")
class Value {
    
    @XStreamAsAttribute
    private String name = "BODY";

    private Block block;
    
    Value(Block block) {
        this.block = block;
    }

    Value(String name, Block block) {
        this.name = name;
        this.block = block;
    }

}
