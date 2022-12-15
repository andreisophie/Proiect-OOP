import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import database.Database;
import helpers.Helpers;
import input.ActionsInput;
import input.Input;

public final class Main {
    private Main() { }

    /**
     * Primary function, entry point to the POO TV implementation
     * @param args args[0] should contain input filepath, args[1] should contain output filepath
     * @throws IOException Exception if filepaths received as arguments are incorrect
     */
    public static void main(final String[] args) throws IOException {
        final String inFile = args[0];
        final String outFile = args[1];

        final ObjectMapper objectMapper = new ObjectMapper();
        final Input inputData = objectMapper.readValue(new File(inFile), Input.class);

        final ArrayNode output = objectMapper.createArrayNode();

        // load users and movies from database into memory
        Database.cleanupDatabase();
        Database.initializeDatabase(inputData);
        // run each action sequentially
        for (final ActionsInput actionInput : inputData.getActions()) {
            Database.getInstance().setCurrentAction(actionInput);
            Helpers.runAction(output);
        }

        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(outFile), output);
    }
}
