package es.us.lsi.restest.engine;

import com.mashape.unirest.http.HttpResponse;
import es.us.lsi.restest.controllers.RequestController;
import org.json.JSONException;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


public class Assertions {
    public static AbstractMap<String, Boolean> resultAssertions = new HashMap<>();
    public static AbstractMap<String, Boolean> resultAssertionsHeaders = new HashMap<>();
    public static AbstractMap<String, Boolean> resultAssertionsBody = new HashMap<>();

    public static AbstractMap<String, Boolean> executeTests(Object test, HttpResponse<InputStream> jsonResponse, Long responseTime, StringBuilder response) {
        try {
            AbstractMap<String, String> potentialTest = RequestAnswer.parser(test);
            resultAssertions = new HashMap<>();
            resultAssertionsHeaders = new HashMap<>();
            for (AbstractMap.Entry<String, String> entry : potentialTest.entrySet()) {
                switch (entry.getKey().toUpperCase()) {
                    case "STATUS-CODE":
                        resultAssertions.put("Status code test", compareStatusCode(entry.getValue(), jsonResponse));
                        break;
                    case "RESPONSE-TIME":
                        resultAssertions.put("Response time test", compareResponseTime(entry.getValue(), responseTime));
                        break;
                    case "CONTAINS-HEADER":
                        compareContainsHeader(entry.getValue(), jsonResponse);
                        break;
                    case "BODY-CONTAINS":
                        resultAssertionsBody.put("Contains body", compareBody(entry.getValue(), response));
                        break;
                    case "POSITIVE-POST-REQUEST":
                        resultAssertions.put("Positive Post request", compareSuccessfulPostRequest(jsonResponse));
                }
            }
        } catch (JSONException e) {
            RequestController.exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
        }
        return Assertions.resultAssertions;
    }

    /**
     * Comprueba que la petición que la petición POST que se ha hecho, ha sido satisfactoria
     *
     * @param jsonResponse la respuesta de la peticion
     * @return true si la respuesta ha sido satisfactoria
     */
    private static Boolean compareSuccessfulPostRequest(HttpResponse<InputStream> jsonResponse) {
        return jsonResponse.getStatus() == 201 | jsonResponse.getStatus() == 202;
    }

    private static Boolean compareBody(String toTest, StringBuilder response) {
        String fromResponse = new String(response);
        String[] auz = toTest.substring(1, toTest.length() - 1).split(":");
        String dw = auz[0] + ": " + auz[1];
        Boolean res = fromResponse.matches(toTest);
        return res;
    }

    /**
     * Comprueba que las cabeceras que se pasan por parametros están contenidas en las cabeceras de respuesta
     *
     * @param headerValue  cabeceras a probar
     * @param jsonResponse respuesta completa recibida del servidor
     */
    private static void compareContainsHeader(Object headerValue, HttpResponse<InputStream> jsonResponse) {
        AbstractMap<String, String> headersToTest = RequestAnswer.parser(headerValue);
        resultAssertionsHeaders = new HashMap<>();
        resultAssertionsHeaders.put("Contains headers test", null);
        for (Map.Entry<String, String> entry : headersToTest.entrySet()) {
            if (jsonResponse.getHeaders().keySet().contains(entry.getKey().toLowerCase())) {
                resultAssertionsHeaders.put(entry.getKey(), jsonResponse.getHeaders().get(entry.getKey().toLowerCase()).contains(entry.getValue()));
            }
        }
    }

    /**
     * Comprueba que el tiempo de respuesta es menor a un tiempo dado
     *
     * @param test         tiempo a probar
     * @param responseTime tiempo que ha tardado la llamada
     * @return true test es menor que responseTime
     */
    private static Boolean compareResponseTime(String test, Long responseTime) {
        return responseTime <= new Long(test);
    }

    /**
     * Comprueba que el codigo de estado es el que se indica en el test
     *
     * @param test         codigo esperado
     * @param jsonResponse respuesta completa del servidor
     * @return true si el codigo recibido es igual que el indicado en el test
     */
    private static Boolean compareStatusCode(String test, HttpResponse<InputStream> jsonResponse) {
        return test.equalsIgnoreCase(Integer.toString(jsonResponse.getStatus()));
    }

}
