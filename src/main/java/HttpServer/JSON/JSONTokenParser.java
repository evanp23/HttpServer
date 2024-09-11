package HttpServer.JSON;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JSONTokenParser {

    public static JsonObject parseJsonObject(Queue<JSONToken> tokens) throws Exception {
        JsonObject object = new JsonObject();
        JSONToken token = tokens.remove();
        if(token.getTokenType() == JSONTokenType.BRACE_OPEN) token = tokens.remove();
        while(token.getTokenType() != JSONTokenType.BRACE_CLOSE){
            if(token.getTokenType() == JSONTokenType.STRING){
                String key = String.valueOf(token.getValue());
                token = tokens.remove();
                if(token.getTokenType() != JSONTokenType.COLON){
                    throw new Exception("Expected colon after key: " + key);
                }
                Object val = parseJsonValue(tokens.remove(), tokens);
                object.put(key, val);
            }
            else {
                throw new Exception("Expected String key in object");
            }
            token = tokens.remove();
            if(token.getTokenType() == JSONTokenType.COMMA) token = tokens.remove();
        }

        return object;
    }

    public static JsonArray parseJsonArray(Queue<JSONToken> tokens) throws Exception {
        JsonArray arr = new JsonArray();
        JSONToken token = tokens.remove();
        if(token.getTokenType() == JSONTokenType.BRACKET_OPEN) token = tokens.remove();
        while(token.getTokenType() != JSONTokenType.BRACKET_CLOSE){
            Object val = parseJsonValue(token, tokens);
            arr.add(val);
            token = tokens.remove();
            if(token.getTokenType() == JSONTokenType.COMMA) token = tokens.remove();
        }
        return arr;
    }

    public static Object parseJsonValue(JSONToken token, Queue<JSONToken> tokens) throws Exception {
        switch(token.getTokenType()){
            case STRING:
                return String.valueOf(token.getValue());
            case NUMBER:
                if(token.getValue() instanceof Integer){
                    return Integer.parseInt(token.getValue().toString());
                }
                else if(token.getValue() instanceof Double){
                    return Double.parseDouble(token.getValue().toString());
                }
                break;
            case TRUE:
                return true;
            case FALSE:
                return false;
            case NULL:
                return null;
            case BRACE_OPEN:
                return parseJsonObject(tokens);
            case BRACKET_OPEN:
                return parseJsonArray(tokens);
            default:
                throw new Exception("Unexpected token - Type: " + token.getTokenType() + ", value: " + token.getValue());
        }
        return null;
    }
}
