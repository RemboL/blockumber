package pl.rembol.blockumber.controllers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("next")
class Next {

    private Block block;
    
    Next(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
