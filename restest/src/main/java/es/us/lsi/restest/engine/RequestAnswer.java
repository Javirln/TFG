package es.us.lsi.restest.engine;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import es.us.lsi.restest.controllers.RequestController;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestAnswer {

    public static final String USER_AGENT = "Mozilla/5.0";
    public static int responseCode = 0;
    public static Map<String, String> generalInfo = new HashMap<>();
    public static AbstractMap<String, String> params = new HashMap<>();
    private static HttpURLConnection con = null;

    // HTTP GET request
    public static void sendGet(String strURL) throws Exception {
        HttpURLConnection con = null;
        try {

            URL url = new URL(strURL);

            Test.syntaxTest(url);


            HttpResponse<InputStream> jsonResponse = Unirest.get(url.toString()).asBinary();

            setValues(url, jsonResponse);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(jsonResponse.getRawBody()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (response.length() != 0) {
                RequestController.responseValues.setResponse(new StringBuilder(response));
            } else {
                RequestController.responseValues.setResponse(new StringBuilder("No data"));
            }
        } catch (IOException io) {
            handlingException(con);
        } finally {

        }
    }


    // HTTP POST request
    public static void sendPost(String strURL, Object params) throws Exception {
        try {
            AbstractMap<String, String> localParams;

            localParams = parser(params);

            URL url = new URL(strURL);

            //Test.syntaxTest(url);

            HttpResponse<InputStream> jsonResponse = Unirest
                    .post(url.toString()).header("Content-Type", "application/x-www-form-urlencoded")
                    .queryString(new HashMap<String, Object>(localParams)).asBinary();


            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse);
            in.close();


            if (response.length() != 0) {
                RequestController.responseValues.setResponse(new StringBuilder(response));
            } else {
                RequestController.responseValues.setResponse(new StringBuilder("No data"));
            }
        } catch (IOException io) {
            handlingException(con);
        } finally {

        }
    }

    private static void setValues(URL url, HttpResponse<InputStream> jsonResponse) throws IOException {
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

        RequestController.responseValues.resetProperties();
        //RequestController.responseValues.setRequestHeaders();
        RequestController.responseValues.setResponseHeaders(jsonResponse.getHeaders());
        RequestController.responseValues.setGeneralInfo(generalInfo);
        RequestController.responseValues.setResponseCode(Integer.toString(responseCode) + " " + jsonResponse.getStatusText());
    }

    private static void handlingException(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getErrorStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        RequestController.responseValues.setResponse(new StringBuilder(response));
    }

    private static String getQuery(AbstractMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> pair : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            System.out.println(pair.getKey());
            System.out.println(pair.getValue());
            result.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        System.out.println(result.toString());

        return result.toString();
    }

    private static AbstractMap<String, String> parser(Object params) {
        AbstractMap<String, String> localParams = new HashMap<>();
        if (params != " ") {
            JSONObject json = new JSONObject(params.toString());
            Iterator<String> iter = json.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = json.get(key);
                    localParams.put(key, value.toString());
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
        }
        return localParams;
    }

    // HTTP PUT request
    public static void sendPut(String strURL, Object params) throws Exception {
        try {
            AbstractMap<String, String> localParams;

            localParams = parser(params);

            URL url = new URL(strURL);

            //Test.syntaxTest(url);

            HttpResponse<InputStream> jsonResponse = Unirest
                    .put(url.toString()).header("Content-Type", "application/x-www-form-urlencoded")
                    .queryString(new HashMap<String, Object>(localParams)).asBinary();


            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse);
            in.close();


            if (response.length() != 0) {
                RequestController.responseValues.setResponse(new StringBuilder(response));
            } else {
                RequestController.responseValues.setResponse(new StringBuilder("No data"));
            }
        } catch (IOException io) {
            handlingException(con);
        }
    }

    //HTTP DELETE request
    public static void sendDelete(String strURL, Object params) throws Exception {
        try {
            AbstractMap<String, String> localParams;

            localParams = parser(params);

            URL url = new URL(strURL);

            //Test.syntaxTest(url);

            HttpResponse<InputStream> jsonResponse = Unirest
                    .delete(url.toString()).header("Content-Type", "application/x-www-form-urlencoded")
                    .queryString(new HashMap<String, Object>(localParams)).asBinary();


            InputStream is = jsonResponse.getRawBody();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            setValues(url, jsonResponse);
            in.close();


            if (response.length() != 0) {
                RequestController.responseValues.setResponse(new StringBuilder(response));
            } else {
                RequestController.responseValues.setResponse(new StringBuilder("No data"));
            }
        } catch (IOException io) {
            handlingException(con);
        }
    }

}