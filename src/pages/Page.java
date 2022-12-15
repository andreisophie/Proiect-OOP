package pages;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Page {
    /**
     * changes a page, if possible, depending on the argument received
     * @param target name of target page
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    public abstract ObjectNode changePage(String target);

    /**
     * executes an action, is possible
     * @param feature name of action to be executed
     * @return JsonNode containing relevant information (if any) after exectuing instruction
     */
    public abstract ObjectNode action(String feature);
}
