package HttpServer.JSON;

public class JSONToken {
    private JSONTokenType tokenType;
    private Object value;

    public JSONToken(JSONTokenType tokenType, Object value){
        this.tokenType = tokenType;
        this.value = value;
    }

    public JSONTokenType getTokenType() {
        return tokenType;
    }

    public Object getValue() {
        return value;
    }
}
