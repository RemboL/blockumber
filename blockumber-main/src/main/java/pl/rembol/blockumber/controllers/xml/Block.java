package pl.rembol.blockumber.controllers.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("block")
public class Block {

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

    private Value value;

    private Next next;

    public Block(String type) {
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
        this.value = new Value(block);
    }

    boolean hasNext() {
        return next != null;
    }

    public Block getNextBlock() {
        return next.getBlock();
    }

    void addNextBlock(Block block) {
        next = new Next(block);
    }
}
