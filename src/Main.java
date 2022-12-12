import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Database;
import helpers.Helpers;
import input.ActionsInput;
import input.Input;

public class Main {
    public static void main(String[] args) throws IOException{
        final String in_file = args[0];
        final String out_file = args[1];

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(in_file), Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        // load users and movies from database into memory
        Database.initializeDatabase(inputData);
        // run each action sequentially
        for (ActionsInput actionInput : inputData.getActions()) {
            Database.getInstance().setCurrentAction(actionInput);
            Helpers.runAction(output);
        }

        Database.cleanupDatabase();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(out_file), output);
    }
}
