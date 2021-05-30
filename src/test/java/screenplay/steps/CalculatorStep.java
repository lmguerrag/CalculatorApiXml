package screenplay.steps;

import co.com.sofka.calculator.test.task.PostRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorStep {

    private Actor actor;
    private String urlBase = "http://www.dneonline.com";
    private String path = "/calculator.asmx";
    private int num1 = 5;
    private int num2 = 8;
    private String body = "<soapenv:Envelope xmlns:soapenv=" +
            "\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> " +
            "<soapenv:Header/> <soapenv:Body> " +
            "<tem:Add> <tem:intA>" + num1 + "</tem:intA> " +
            "<tem:intB>" + num2 + "</tem:intB> </tem:Add> " +
            "</soapenv:Body> </soapenv:Envelope>";

    @Given("un {string} quiere hacer operaciones con dos numeros")
    public void unQuiereHacerOperacionesConDosNumeros(String name) {
        actor = Actor.named(name);
        actor.whoCan(CallAnApi.at(urlBase));
    }

    @When("suma los numeros")
    public void sumaLosNumeros() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Add");
        actor.attemptsTo(PostRequest.execute(headers, path, body));
    }

    @Then("la api entrega el resultado de la suma")
    public void laApiEntregaElResultadoDeLaSuma() {
        actor.should(
                seeThatResponse("el servicio web respondio el codigo esperado",
                        response -> response.statusCode(200)));
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(LastResponse.received().answeredBy(actor).asString());
            String result = xmlJSONObj.getJSONObject("soap:Envelope")
                            .getJSONObject("soap:Body")
                            .getJSONObject("AddResponse")
                            .get("AddResult").toString();
            int resultSuma = num1 + num2;
            assertThat(resultSuma).isEqualTo(Integer.parseInt(result));

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }

    @When("multiplica los numeros")
    public void multiplicaLosNumeros() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "text/xml;charset=UTF-8");
        headers.put("SOAPAction", "http://tempuri.org/Multiply");
        actor.attemptsTo(PostRequest.execute(headers, path, body));
    }

    @Then("la api entrega el resultado de la multiplicacion")
    public void laApiEntregaElResultadoDeLaMultiplicacion() {
        actor.should(
                seeThatResponse("el servicio web respondio el codigo esperado",
                        response -> response.statusCode(200)));
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(LastResponse.received().answeredBy(actor).asString());
            String result = xmlJSONObj.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("AddResponse")
                    .get("AddResult").toString();
            int resultMulti = num1 * num2;
            assertThat(resultMulti).isEqualTo(Integer.parseInt(result));

        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }
}

