package HttpServer.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONTokenizer {

    String json;
    Object POJO;

    public JSONTokenizer(String json){
        this.json = json;
    }

    public JSONTokenizer(Object POJO){
        this.POJO = POJO;
    }

    public Queue<JSONToken> tokenizeJSON() throws Exception {
        Queue<JSONToken> tokens = new LinkedList<>();


        for(int i = 0; i < json.length(); i++){
            char input = json.charAt(i);
            Pattern pattern = Pattern.compile("[\\d\\w.]");
            Matcher matcher = pattern.matcher(Character.toString(input));

            if(input == '{'){
                tokens.add(new JSONToken(JSONTokenType.BRACE_OPEN, input));
            }
            else if(input == '}'){
                tokens.add(new JSONToken(JSONTokenType.BRACE_CLOSE, input));
            }
            else if(input == '['){
                tokens.add(new JSONToken(JSONTokenType.BRACKET_OPEN, input));
            }
            else if(input == ']'){
                tokens.add(new JSONToken(JSONTokenType.BRACKET_CLOSE, input));
            }
            else if(input == ':'){
                tokens.add(new JSONToken(JSONTokenType.COLON, input));
            }
            else if(input == ','){
                tokens.add(new JSONToken(JSONTokenType.COMMA, input));
            }
            else if(input == '"'){
                String value = "";
                input = json.charAt(++i);
                while(input != '\"'){
                    value += input;
                    input = json.charAt(++i);
                }
                tokens.add(new JSONToken(JSONTokenType.STRING, value));
            }
            else if(matcher.matches()){
                String value = "";
                while(true){
                    value += input;
                    input = json.charAt(++i);
                    matcher = pattern.matcher(Character.toString(input));
                    if(!matcher.matches()){ i--; break;}
                }

                boolean isNumeric = true;
                try{
                    Integer val = Integer.parseInt(value);
                    tokens.add(new JSONToken(JSONTokenType.INTEGER, val));
                } catch(NumberFormatException n){
                    try {
                        Double val = Double.parseDouble(value);
                        tokens.add(new JSONToken(JSONTokenType.DOUBLE, val));
                    } catch (NumberFormatException f){
                        isNumeric = false;
                    }
                }
                if(!isNumeric){
                    if(value.equalsIgnoreCase("true")){
                        tokens.add(new JSONToken(JSONTokenType.TRUE, true));
                    }
                    else if(value.equalsIgnoreCase("false")){
                        tokens.add(new JSONToken(JSONTokenType.FALSE, false));
                    }
                    else if(value.equalsIgnoreCase("null")){
                        tokens.add(new JSONToken(JSONTokenType.NULL, null));
                    }
                    else{
                        throw new Exception("Unexpected token value: " + value);
                    }
                }
            }
        }

        return tokens;
    }

    public Queue<JSONToken> tokenizePOJO(List<Object> array) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Queue<JSONToken> jsonTokens = new LinkedList<>();
        Object[] fieldsValues;
        if(array == null) {
            fieldsValues = POJO.getClass().getDeclaredFields();
            jsonTokens.add(new JSONToken(JSONTokenType.BRACE_OPEN, '{'));
        }
        else{
            fieldsValues = array.toArray();
        }
        for(int i = 0; i < fieldsValues.length; i++){
            Field field;
            String fieldName;
            String getterName;
            Class fieldType;
            Boolean arr = false;
            if(array == null) {
                field = (Field) fieldsValues[i];
                fieldName = field.getName();
                getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                fieldType = field.getType();
                jsonTokens.add(new JSONToken(JSONTokenType.STRING, fieldName));
                jsonTokens.add(new JSONToken(JSONTokenType.COLON, ':'));
            }
            else{
                arr = true;
                fieldName = null;
                getterName = null;
                fieldType = array.get(0).getClass();
            }

            //field is a String
            if(fieldType.isAssignableFrom(String.class)){
                String value = arr ? (String) fieldsValues [i] : (String) getPOJOValue(getterName);
                jsonTokens.add(new JSONToken(JSONTokenType.STRING, value));
            }
            //field is an integer
            else if(fieldType.isAssignableFrom(Integer.class)){
                Integer value = (Integer) getPOJOValue(getterName);
                jsonTokens.add(new JSONToken(JSONTokenType.INTEGER, value));
            }
            //field is a double
            else if(fieldType.isAssignableFrom(Double.class)){
                Double value = arr ? (Double) fieldsValues [i] :  (Double) getPOJOValue(getterName);
                jsonTokens.add(new JSONToken(JSONTokenType.DOUBLE, value));
            }
            //field is a boolean
            else if(fieldType.isAssignableFrom(Boolean.class)){
                Boolean value = arr ? (Boolean) fieldsValues [i] :  (Boolean) getPOJOValue(getterName);
                if(value) {
                    jsonTokens.add(new JSONToken(JSONTokenType.TRUE, true));
                }
                else{
                    jsonTokens.add(new JSONToken(JSONTokenType.FALSE, false));
                }
            }
            //field is an array
            else if(fieldType.isAssignableFrom(List.class)){
                jsonTokens.add(new JSONToken(JSONTokenType.BRACKET_OPEN, fieldName));
                jsonTokens.addAll(tokenizePOJO((List<Object>) getPOJOValue(getterName)));
                jsonTokens.add(new JSONToken(JSONTokenType.BRACKET_CLOSE, fieldName));
            }
            if(array == null && i != fieldsValues.length - 1) jsonTokens.add(new JSONToken(JSONTokenType.COMMA, ','));


        }
        if(array == null) jsonTokens.add(new JSONToken(JSONTokenType.BRACE_CLOSE, '}'));
        return jsonTokens;
    }

    private Object getPOJOValue(String getterName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return POJO.getClass().getDeclaredMethod(getterName).invoke(POJO);
    }
}
