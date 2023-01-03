package com.numble.carot.common.util;

import com.numble.carot.exception.IllegalArgumentKeyException;
import com.numble.carot.exception.InvalidResourceException;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class TextTemplateEngine {

    private static final String ALLOWED_KEY_CHARACTER = "a-zA-Z_";
    private static final Pattern TEMPLATE_VARIABLE = Pattern.compile("@\\{([" + ALLOWED_KEY_CHARACTER + "]+)}");
    private final HashMap<String, String> arguments;

    private TextTemplateEngine(HashMap<String, String> arguments) {
        this.arguments = arguments;
    }

    private String replaceToArgumentValue(String input){
        return TEMPLATE_VARIABLE.matcher(input).replaceAll(mr -> arguments.get(mr.group(1)));
    }

    public String readHtmlFromResource(String htmlResourcePath) {
        ClassPathResource resource = new ClassPathResource(htmlResourcePath);
        List<String> lines = new ArrayList<>();
        try{
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);
            String buffer;
            while((buffer = br.readLine()) != null){
                lines.add(buffer);
            }
        } catch (IOException e) {
            throw new InvalidResourceException("리소스 파일을 읽을 수 없습니다 :" + htmlResourcePath +e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            sb.append(replaceToArgumentValue(line)).append('\n');
        }
        return sb.toString();
    }

    //builder
    public static class Builder{
        //replaceAll 과정은 해당 키만 허용한다.
        private static final Pattern NOT_ALLOWED_KEY_CHARACTER = Pattern.compile("[^" + ALLOWED_KEY_CHARACTER + "]");
        private final HashMap<String, String> arguments = new HashMap<>();

        public Builder argument(String key, String value){
            if(NOT_ALLOWED_KEY_CHARACTER.matcher(key).matches()){
                throw new IllegalArgumentKeyException("Key 에 사용할 수 없는 문자가 있습니다.");
            }
            arguments.put(key, value);
            return this;
        }

        public TextTemplateEngine build(){
            return new TextTemplateEngine(arguments);
        }
    }
}
