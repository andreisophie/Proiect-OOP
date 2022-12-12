package database;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JSONable {
    ObjectNode toJSON();
}
