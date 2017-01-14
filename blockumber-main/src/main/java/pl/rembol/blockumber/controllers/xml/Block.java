package pl.rembol.blockumber.controllers.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("block")
class Block {

    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private Integer x;

    @XStreamAsAttribute
    private Integer y;

    @XStreamImplicit
    private List<Field> field = new ArrayList<>();

    private Statement statement;

    @XStreamImplicit
    private List<Value> value = new ArrayList<>();

    private Next next;

    Block(String type) {
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }
    
    void addToStatement(Block block) {
        if (statement == null) {
            statement = new Statement("BODY");
        }
        statement.addBlock(block);
    }

    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void addField(Field field) {
        this.field.add(field);
    }

    void addValueWithBlock(Block block) {
        this.value.add(new Value(block));
    }

    void addValueWithBlock(String name, Block block) {
        this.value.add(new Value(name, block));
    }

    boolean hasNext() {
        return next != null;
    }

    Block getNextBlock() {
        return next.getBlock();
    }

    void addNextBlock(Block block) {
        next = new Next(block);
    }
}
