import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import database.Database;
import input.Input;

public class Main {
    public static void main(String[] args) throws IOException{
        final String in_file = args[0];
        final String out_file = args[1];

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(in_file), Input.class);

        // TODO: I will add here entry point to my implementation
        Database.initializeDatabase(inputData);
        
        //

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(out_file), inputData);
    }
}
