package HttpServer.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonObject extends HashMap<Object, Object> {
    public JsonObject(String jsonString) throws Exception {
        JSONTokenizer tokenizer = new JSONTokenizer(jsonString);
        this.putAll(JSONTokenParser.parseJsonObject(tokenizer.tokenizeJSON()));
    }

    public Object convertTo(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Field [] fields = clazz.getDeclaredFields();
        Object o = clazz.getDeclaredConstructors()[0].newInstance();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            String fieldName = field.getName();
            String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Object value = this.get(fieldName);
            o.getClass().getDeclaredMethod(setterName, value.getClass()).invoke(o, value);
            System.out.println("h");

        }
        return o;
    }

    JsonObject(){

    }
}
