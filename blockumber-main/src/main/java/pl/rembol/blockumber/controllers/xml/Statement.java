package pl.rembol.blockumber.controllers.xml;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Statement {

    @XStreamAsAttribute
    private String name;

    private Block block;
    
    Statement(String name) {
        this.name = name;
    }
    
    void addBlock(Block block) {
        if (this.block == null) {
            this.block = block;
        } else {
            Block bottomBlock = this.block;
            while (bottomBlock.hasNext()) {
                bottomBlock = bottomBlock.getNextBlock();
            }

            bottomBlock.addNextBlock(block);
        }
    }

}
