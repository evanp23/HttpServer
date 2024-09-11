package HttpServer.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonObject extends HashMap<Object, Object> {
    public JsonObject(String jsonString) throws Exception {
        JSONTokenizer tokenizer = new JSONTokenizer(jsonString);
        this.putAll(JSONTokenParser.parseJsonObject(tokenizer.tokenizeJSON()));
    }

    JsonObject(){

    }
}
