package HttpServer.JSON;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JsonArray extends ArrayList<Object> {
    List<JSONTokenType> concurrentTokenTypes;

    Queue<JSONToken> jsonTokens;

    public JsonArray(){
        this.concurrentTokenTypes = new ArrayList<>();
    }

    public JsonArray(String jsonString) throws Exception {
        this.concurrentTokenTypes = new ArrayList<>();
        this.jsonTokens = JSONTokenizer.tokenizeJSON(jsonString);
        this.addAll(JSONTokenParser.parseJsonArray(new LinkedList<>(this.jsonTokens)));
    }

    public List<JSONTokenType> getConcurrentTokenTypes() {
        return concurrentTokenTypes;
    }

    public void setConcurrentTokenTypes(List<JSONTokenType> concurrentTokenTypes) {
        this.concurrentTokenTypes = concurrentTokenTypes;
    }

    public void addTokenType(JSONTokenType tokenType){
        this.concurrentTokenTypes.add(tokenType);
    }

    public Queue<JSONToken> getJsonTokens() {
        return jsonTokens;
    }

    public void setJsonTokens(Queue<JSONToken> jsonTokens) {
        this.jsonTokens = jsonTokens;
    }
}
