package es.us.lsi.restest.engine;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import es.us.lsi.restest.utils.APIResponse;
import groovy.json.JsonException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static es.us.lsi.restest.controllers.RequestController.exceptionMessages;

@Service
public class RequestAnswer {
    private static final Long CONNECTION_TIMEOUT = 10000L;
    private static final Long SOCKET_TIMEOUT = 10000L;
    public static APIResponse responseValues = APIResponse.getInstance();
    public static Map<String, String> generalInfo = new HashMap<>();

    /**
     * Envia una peticion GET a la URL y devuelve toda la informacion disponible sobre el cuerpo, cabeceras, conexion y
     * tiempo
     *
     * @param strURL            url donde se va a hacer la peticion
     * @param headers           cabeceras que se quieran agregar a la peticion
     * @param connectionTimeout connectionTimeout que se quiere para la llamada
     * @param socketTimeout     socketTimeout que se quiere para la llamada
     * @param testsToPerform    los tests que se quieran realizar relacionados con la llamada y el resultado de la misma
     */
    public static void sendGet(String strURL, Object headers, Integer connectionTimeout, Integer socketTimeout, Object testsToPerform) {
        try {
            AbstractMap<String, String> localHeaders;

            Long localTimeout;
            Long localSocketTimeout;

            localHeaders = parser(headers);

            URL url = new URL(strURL);

            SemanticAnalysis.checkURL(url);

            if (connectionTimeout == null) {
                localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
            } else {
                Long aux = new Long(connectionTimeout);
                if (aux < 0) {
                    localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
                } else {
                    localTimeout = aux;
                }
            }

            if (socketTimeout == null) {
                localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
            } else {
                Long aux = new Long(socketTimeout);
                if (aux < 0) {
                    localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
                } else {
                    localSocketTimeout = aux;
                }
            }

            Unirest.setTimeouts(localTimeout, localSocketTimeout);

            long startTime = System.currentTimeMillis();

            HttpResponse<InputStream> jsonResponse = Unirest.get(url.toString()).headers(localHeaders).asBinary();

            long elapsedTime = System.currentTimeMillis() - startTime;


            setValues(url, jsonResponse, localHeaders, elapsedTime);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(jsonResponse.getRawBody()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (response.length() != 0) {
                responseValues.setResponse(new StringBuilder(response));
            } else {
                responseValues.setResponse(new StringBuilder("No data"));
            }

            Assertions.executeTests(testsToPerform, jsonResponse, elapsedTime, response);
        } catch (UnirestException | SocketTimeoutException e) {
            e.printStackTrace();
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (MalformedURLException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (JsonException e) {
            exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
        }
    }


    /**
     * @param strURL
     * @param params
     * @param headers
     * @param connectionTimeout
     * @param socketTimeout
     * @param testsToPerform
     */
    public static void sendPost(String strURL, Object params, Object headers, Integer connectionTimeout, Integer socketTimeout, Object testsToPerform) {
        try {
            AbstractMap<String, String> localParams;
            AbstractMap<String, String> localHeaders;

            Long localTimeout;
            Long localSocketTimeout;

            localParams = parser(params);
            localHeaders = parser(headers);

            URL url = new URL(strURL);

            SemanticAnalysis.checkURL(url);


            if (connectionTimeout == null) {
                localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
            } else {
                Long aux = new Long(connectionTimeout);
                if (aux < 0) {
                    localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
                } else {
                    localTimeout = aux;
                }
            }

            if (socketTimeout == null) {
                localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
            } else {
                Long aux = new Long(socketTimeout);
                if (aux < 0) {
                    localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
                } else {
                    localSocketTimeout = aux;
                }
            }

            Unirest.setTimeouts(localTimeout, localSocketTimeout);

            long startTime = System.currentTimeMillis();

            HttpResponse<InputStream> jsonResponse = Unirest
                    .post(url.toString()).headers(localHeaders).header("Content-Type", "application/x-www-form-urlencoded")
                    .fields(new HashMap<String, Object>(localParams)).asBinary();

            long elapsedTime = System.currentTimeMillis() - startTime;

            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse, localHeaders, elapsedTime);
            in.close();


            if (response.length() != 0) {
                responseValues.setResponse(new StringBuilder(response));
            } else {
                responseValues.setResponse(new StringBuilder("No data"));
            }
            Assertions.executeTests(testsToPerform, jsonResponse, elapsedTime, response);
        } catch (UnirestException | SocketTimeoutException e) {
            e.printStackTrace();
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (MalformedURLException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (JsonException e) {
            exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
        }
    }

    // HTTP PUT request
    public static void sendPut(String strURL, Object params, Object headers, Integer connectionTimeout, Integer socketTimeout, Object testsToPerform) {
        try {
            AbstractMap<String, String> localParams;
            AbstractMap<String, String> localHeaders;

            Long localTimeout;
            Long localSocketTimeout;

            localParams = parser(params);
            localHeaders = parser(headers);

            URL url = new URL(strURL);

            SemanticAnalysis.checkURL(url);


            if (connectionTimeout == null) {
                localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
            } else {
                Long aux = new Long(connectionTimeout);
                if (aux < 0) {
                    localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
                } else {
                    localTimeout = aux;
                }
            }

            if (socketTimeout == null) {
                localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
            } else {
                Long aux = new Long(socketTimeout);
                if (aux < 0) {
                    localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
                } else {
                    localSocketTimeout = aux;
                }
            }

            long startTime = System.currentTimeMillis();

            Unirest.setTimeouts(localTimeout, localSocketTimeout);

            HttpResponse<InputStream> jsonResponse = Unirest
                    .put(url.toString()).headers(localHeaders).header("Content-Type", "application/x-www-form-urlencoded")
                    .fields(new HashMap<String, Object>(localParams)).asBinary();

            long elapsedTime = System.currentTimeMillis() - startTime;

            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse, localHeaders, elapsedTime);
            in.close();


            if (response.length() != 0) {
                responseValues.setResponse(new StringBuilder(response));
            } else {
                responseValues.setResponse(new StringBuilder("No data"));
            }
            Assertions.executeTests(testsToPerform, jsonResponse, elapsedTime, response);
        } catch (UnirestException | SocketTimeoutException e) {
            e.printStackTrace();
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (MalformedURLException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (JsonException e) {
            exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
        }
    }

    //HTTP DELETE request
    public static void sendDelete(String strURL, Object params, Object headers, Integer connectionTimeout, Integer socketTimeout, Object testsToPerform) {
        try {
            AbstractMap<String, String> localParams;
            AbstractMap<String, String> localHeaders;

            Long localTimeout;
            Long localSocketTimeout;

            localParams = parser(params);
            localHeaders = parser(headers);

            URL url = new URL(strURL);

            SemanticAnalysis.checkURL(url);


            if (connectionTimeout == null) {
                localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
            } else {
                Long aux = new Long(connectionTimeout);
                if (aux < 0) {
                    localTimeout = RequestAnswer.CONNECTION_TIMEOUT;
                } else {
                    localTimeout = aux;
                }
            }

            if (socketTimeout == null) {
                localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
            } else {
                Long aux = new Long(socketTimeout);
                if (aux < 0) {
                    localSocketTimeout = RequestAnswer.SOCKET_TIMEOUT;
                } else {
                    localSocketTimeout = aux;
                }
            }
            long startTime = System.currentTimeMillis();

            Unirest.setTimeouts(localTimeout, localSocketTimeout);

            HttpResponse<InputStream> jsonResponse = Unirest
                    .delete(url.toString()).headers(localHeaders).header("Content-Type", "application/x-www-form-urlencoded")
                    .fields(new HashMap<String, Object>(localParams)).asBinary();

            long elapsedTime = System.currentTimeMillis() - startTime;

            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse, localHeaders, elapsedTime);
            in.close();


            if (response.length() != 0) {
                responseValues.setResponse(new StringBuilder(response));
            } else {
                responseValues.setResponse(new StringBuilder("No data"));
            }
            Assertions.executeTests(testsToPerform, jsonResponse, elapsedTime, response);
        } catch (UnirestException | SocketTimeoutException e) {
            e.printStackTrace();
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (MalformedURLException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            exceptionMessages.put("url", "The URL is not well formed.");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            exceptionMessages.put("con", "This seems to be like an error connecting to ");
        } catch (JsonException e) {
            exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
        }
    }

    private static void setValues(URL url, HttpResponse<InputStream> jsonResponse,
                                  AbstractMap<String, String> localHeaders, Long responseTime) {
        generalInfo = new HashMap<>();

        int responseCode = jsonResponse.getStatus();

        generalInfo.put("Protocol", url.getProtocol());
        generalInfo.put("Authority", url.getAuthority());
        generalInfo.put("Host", url.getHost());
        generalInfo.put("Default Port", Integer.toString(url.getDefaultPort()));
        generalInfo.put("Port ", Integer.toString(url.getPort()));
        generalInfo.put("Path", url.getPath());
        generalInfo.put("Query", url.getQuery());
        generalInfo.put("Filename", url.getFile());
        generalInfo.put("Ref", url.getRef());

        responseValues.resetProperties();
        responseValues.setRequestHeaders(localHeaders);
        responseValues.setResponseHeaders(jsonResponse.getHeaders());
        responseValues.setGeneralInfo(generalInfo);
        responseValues.setResponseTime(responseTime);
        responseValues.setResponseCode(Integer.toString(responseCode) + " " + jsonResponse.getStatusText());
    }

    public static AbstractMap<String, String> parser(Object params) throws JSONException {
        AbstractMap<String, String> localParams = new HashMap<>();
        if (params != "") {
            JSONObject json = new JSONObject(params.toString());
            Iterator<String> iter = json.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = json.get(key);
                    localParams.put(key, value.toString());
                } catch (JSONException e) {
                    exceptionMessages.put("parser", "There has been a problem parsing your custom values (params, request headers or tests).");
                }
            }
        }
        return localParams;
    }

}