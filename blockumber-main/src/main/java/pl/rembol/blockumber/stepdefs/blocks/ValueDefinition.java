package pl.rembol.blockumber.stepdefs.blocks;

public interface ValueDefinition extends InputDefinition {

    default String getCheck() {
        return null;
    }

    default String getText() {
        return "";
    }
}
