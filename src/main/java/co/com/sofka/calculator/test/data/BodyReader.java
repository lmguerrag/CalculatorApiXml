package co.com.sofka.calculator.test.data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class BodyReader {

    public String getBody() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(System.getProperty("user.dir") + "src/main/resources/xml.json" );
        JSONObject obj = (JSONObject) jsonParser.parse(reader);
        return obj.get("xml").toString();
    }
}
