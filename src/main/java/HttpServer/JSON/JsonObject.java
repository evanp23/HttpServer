package HttpServer.JSON;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
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
        this.jsonTokens = JSONTokenizer.tokenizeJSON(jsonString);
        this.putAll(JSONTokenParser.parseJsonObject(new LinkedList<>(this.jsonTokens)));
    }

    public JsonObject(Object pojo) throws Exception {
        this.objectTypes = new HashMap<>();
        this.jsonTokens = JSONTokenizer.tokenizePOJO(pojo, null);
        this.putAll(JSONTokenParser.parseJsonObject(new LinkedList<>(this.jsonTokens)));
    }

    public Object convertTo(Class clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Object> jsonProperties = Arrays.asList(this.keySet().toArray());
        Object o = clazz.getDeclaredConstructors()[0].newInstance();
        for(Object jsonProperty : jsonProperties){
            String propertyName = (String) jsonProperty;
            String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            Object value = this.get(propertyName);
            Class valClass = null;
            Field field = null;
            try {
                field = clazz.getDeclaredField(propertyName);
                valClass = value.getClass();
            } catch(NoSuchFieldException n){
                logger.error("No variable with name: [" + propertyName + "]" + " found on " + clazz + ":");
                n.printStackTrace(System.out);
                return null;
            }
            if(value instanceof JsonArray) valClass = List.class;
            if(value instanceof JsonObject){
                value = ((JsonObject) value).convertTo(field.getType());
                if(value == null) return null;
                valClass = value.getClass();
            }
            try {
                o.getClass().getDeclaredMethod(setterName, valClass).invoke(o, value);
            } catch(NoSuchMethodException n){
                /*
                    TODO: Account for these Scenarios:
                    1. The data type of the value given in the json is not the same as the one declared in the setter method
                    2. The setter actually doesn't exist (by name)
                 */
                logger.error("No setter for property with name: [" + propertyName + "] found on " + clazz + ":");
                n.printStackTrace(System.out);
                return null;
            }

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
                jsonString.append("\"" + value + "\"");
            }
            else{
                jsonString.append(value);
            }
        }

        return jsonString.toString();
    }

}


