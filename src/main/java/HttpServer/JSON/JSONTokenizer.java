package HttpServer.JSON;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONTokenizer {

    String json;

    public JSONTokenizer(String json){
        this.json = json;
    }

    public Queue<JSONToken> tokenizeJSON() throws Exception {
        Queue<JSONToken> tokens = new LinkedList<>();


        for(int i = 0; i < json.length(); i++){
            char input = json.charAt(i);
            Pattern pattern = Pattern.compile("[\\d\\w]");
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
                while(matcher.matches()){
                    value += input;
                    input = json.charAt(++i);
                    matcher = pattern.matcher(Character.toString(input));
                }

                boolean isNumeric = true;
                try{
                    Integer val = Integer.parseInt(value);
                    tokens.add(new JSONToken(JSONTokenType.NUMBER, val));
                } catch(NumberFormatException n){
                    isNumeric = false;
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
}
