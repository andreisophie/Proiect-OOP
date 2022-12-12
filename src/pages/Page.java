package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Page {
    public abstract ObjectNode changePage(String target);
    public abstract ObjectNode action(String feature);
}
