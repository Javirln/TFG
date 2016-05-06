package es.us.lsi.restest.engine;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.mashape.unirest.http.HttpResponse;
import es.us.lsi.restest.controllers.RequestController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class Assertions {
    public static AbstractMap<String, Boolean> resultAssertions = new HashMap<>();
    public static AbstractMap<String, Boolean> resultAssertionsHeaders = new HashMap<>();
    public static LinkedListMultimap<String, Boolean> resultAssertionsBody = LinkedListMultimap.create();
    public static LinkedListMultimap<String, String> localParams = LinkedListMultimap.create();

    public static AbstractMap<String, Boolean> executeTests(Object test, HttpResponse<InputStream> jsonResponse, Long responseTime, StringBuilder response) {
        try {
            AbstractMap<String, String> potentialTest = RequestAnswer.parser(test);
            resultAssertions = new HashMap<>();
            resultAssertionsHeaders = new HashMap<>();
            resultAssertionsBody = LinkedListMultimap.create();
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
                        compareBody(entry.getValue(), response);
                        break;
                    case "POSITIVE-POST-REQUEST":
                        resultAssertions.put("Positive Post request", compareSuccessfulPostRequest(jsonResponse));
                        break;
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

    /**
     * Comprueba que el cuerpo de un mensaje contiene los parámetros esperados
     *
     * @param toTest   JSON que se espera
     * @param response JSON que se recibe de la respuesta
     */
    private static void compareBody(String toTest, StringBuilder response) {
        Multimap<String, String> toTestMap = parserExtended(toTest);
        clearLocalParams();
        Multimap<String, String> responseMap = parserExtended(response);
        clearLocalParams();
        resultAssertionsBody.put("Body contains test", null);
        for (Map.Entry<String, Collection<String>> entry : toTestMap.asMap().entrySet()) {
            if (responseMap.keySet().contains(entry.getKey())) {
                for (String aux : toTestMap.asMap().get(entry.getKey())) {
                    LinkedList<String> list = new LinkedList<>(responseMap.asMap().get(entry.getKey()));
                    for (String auxList : list) {
                        if (auxList.equals(aux)) {
                            resultAssertionsBody.put(entry.getKey(), true);
                        } else if (list.getLast().equals(auxList)) {
                            resultAssertionsBody.put(entry.getKey(), false);
                        }
                    }
                }
            }
        }
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

    private static Multimap<String, String> parserExtended(Object params) throws JSONException {
        if (params != "") {
            JSONObject json = new JSONObject(params.toString());
            Iterator<String> iter = json.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = json.get(key);
                    if (value.toString().startsWith("{")) {
                        parserExtended(value.toString());
                    } else if (value.toString().startsWith("[{")) {
                        JSONArray elementsOnArray = json.getJSONArray(key);
                        for (int i = 0; i <= elementsOnArray.length() - 1; i++) {
                            if (!elementsOnArray.get(i).equals(null)) {
                                parserExtended(elementsOnArray.get(i).toString());
                            }
                        }
                    }
                    localParams.put(key, value.toString());
                } catch (JSONException e) {
                    RequestController.exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
                }
            }
        }
        return localParams;
    }

    private static void clearLocalParams() {
        localParams = LinkedListMultimap.create();
    }
}