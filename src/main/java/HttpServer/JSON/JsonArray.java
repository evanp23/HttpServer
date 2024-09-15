package HttpServer.JSON;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends ArrayList<Object> {
    List<JSONTokenType> concurrentTokenTypes;

    public JsonArray(){
        this.concurrentTokenTypes = new ArrayList<>();
    }

    public JsonArray(String jsonString) throws Exception {
        this.concurrentTokenTypes = new ArrayList<>();
        JSONTokenizer tokenizer = new JSONTokenizer(jsonString);
        this.addAll(JSONTokenParser.parseJsonArray(tokenizer.tokenizeJSON()));
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
}
