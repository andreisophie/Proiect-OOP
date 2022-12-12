package helpers;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import input.ActionsInput;
import pages.LoginPage;
import pages.RegisterPage;

public class Helpers {
    private Helpers() {}

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static public ArrayNode StringListToJSON(ArrayList<String> list) {
        ArrayNode listNode = Helpers.objectMapper.createArrayNode();
        for (String string : list) {
            listNode.add(string);
        }
        return listNode;
    }

    public static void runAction(ActionsInput actionInput, ArrayNode output) {
        if (actionInput.getType().equals("change page")) {
            // run a change page command
            ObjectNode result = null;
            switch (actionInput.getPage()) {
                case "login" -> result = GlobalState.getInstance().getCurrentPage().changePage(new LoginPage());
                case "register" -> result = GlobalState.getInstance().getCurrentPage().changePage(new RegisterPage());
                default -> System.out.println("Invalid page:" + actionInput.getPage());
            }
            if (result != null) {
                output.add(result);
            }
        } else {
            // run an on-page command
        }
    }
}
