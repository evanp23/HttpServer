package HttpServer.JSON;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JsonObject extends HashMap<Object, Object> {

    protected static final Logger logger = LogManager.getLogger(JsonObject.class);

    private Map<String, JSONTokenType> objectTypes;

    private Queue<JSONToken> jsonTokens;

    public JsonObject() {
        this.objectTypes = new HashMap<>();
    }


    public JsonObject(String jsonString) throws Exception {
        this.objectTypes = new HashMap<>();
        JSONTokenizer tokenizer = new JSONTokenizer(jsonString);
        this.jsonTokens = tokenizer.tokenizeJSON();
        this.putAll(JSONTokenParser.parseJsonObject(new LinkedList<>(this.jsonTokens)));
    }

    public JsonObject(Object pojo) throws Exception {
        this.objectTypes = new HashMap<>();
        JSONTokenizer tokenizer = new JSONTokenizer(pojo);
        this.jsonTokens = tokenizer.tokenizePOJO(null);
        this.putAll(JSONTokenParser.parseJsonObject(new LinkedList<>(this.jsonTokens)));
    }

    public Object convertTo(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field [] fields = clazz.getDeclaredFields();
        Object o = clazz.getDeclaredConstructors()[0].newInstance();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            String fieldName = field.getName();
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Object value = this.get(fieldName);
            Class valClass = value.getClass();
            if(value instanceof JsonArray) valClass = List.class;
            o.getClass().getDeclaredMethod(setterName, valClass).invoke(o, value);

        }
        return o;
    }

    public Map<String, JSONTokenType> getObjectTypes(){
        return this.objectTypes;
    }

    public void setObjectTypes(Map<String, JSONTokenType> objectTypes){
        this.objectTypes = objectTypes;
    }

    public void putObjectType(String objectKey, JSONTokenType tokenType){
        this.objectTypes.put(objectKey, tokenType);
    }

    public String getString(String key){
        return (String) this.get(key);
    }

    public Integer getInt(String key){
        return (Integer) this.get(key);
    }

    public Double getDouble(String key){
        Object val = this.get(key);
        try {
            return (Double) this.get(key);
        } catch(ClassCastException c){
            try{
                return Double.parseDouble(String.valueOf(val));
            } catch(Exception e){
                logger.error("Cannot convert " + val + " to type Double.");
                e.printStackTrace();
                return null;
            }
        }
    }

    public Boolean getBoolean(String key){
        return (Boolean) this.get(key);
    }

    public JsonArray getJsonArray(String key){
        return (JsonArray) this.get(key);
    }

    public Queue<JSONToken> getJsonTokens() {
        return jsonTokens;
    }

    public void setJsonTokens(Queue<JSONToken> jsonTokens) {
        this.jsonTokens = new LinkedList<>(jsonTokens);
        System.out.println("JsonTokens set : " + jsonTokens);
    }

    @Override
    public String toString(){
        //clone tokens so they are not affected by queue traversal
        Queue<JSONToken> tokens = new LinkedList<>(jsonTokens);

        StringBuilder jsonString = new StringBuilder();

        while(tokens.peek() != null){
            JSONToken token = tokens.remove();
            JSONTokenType tokenType = token.getTokenType();
            Object value = token.getValue();

            if(tokenType == JSONTokenType.STRING){
                jsonString.append("\"" + token.getValue() + "\"");
            }
            else{
                jsonString.append(token.getValue());
            }
        }

        return jsonString.toString();
    }

}


