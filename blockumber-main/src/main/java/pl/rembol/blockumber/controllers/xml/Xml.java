package pl.rembol.blockumber.controllers.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("xml")
class Xml {
    @XStreamImplicit
    private List<Block> block = new ArrayList<>();

    void addBlock(Block block) {
        this.block.add(block);
    }
}
