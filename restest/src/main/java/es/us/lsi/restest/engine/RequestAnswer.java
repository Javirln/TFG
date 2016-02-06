package es.us.lsi.restest.engine;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class RequestAnswer {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        RequestAnswer http = new RequestAnswer();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

        System.out.println("\nTesting 3 - Send Http PUT request");
        http.sendPut();

        System.out.println("\nTesting 4 - Send Http DELETE request");
        http.sendDelete();

    }


    // HTTP GET request
    private void sendGet() throws Exception {
        String strURL = "http://api.openweathermap.org:80/data/2.5/weather?q=Seville,ES&format=json&appid=2de143494c0b295cca9337e1e96b00e0";
        //String strURL = "http://jsonplaceholder.typicode.com/posts/1";

        URL url = new URL(strURL);

        Test.syntaxTest(url);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        //get request headers
        Map<String, List<String>> requestHeaders = con.getRequestProperties();
        //get response  headers
        Map<String, List<String>> responseHeaders = con.getHeaderFields();


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + strURL);
        System.out.println("URL data: ");
        System.out.println("\n--------------------------");

        System.out.println("Protocol = " + url.getProtocol());
        System.out.println("Authority = " + url.getAuthority());
        System.out.println("Host = " + url.getHost());
        System.out.println("Port = " + url.getPort());
        System.out.println("Path = " + url.getPath());
        System.out.println("Query = " + url.getQuery());
        System.out.println("Filename = " + url.getFile());
        System.out.println("Ref = " + url.getRef());
        System.out.println("\n--------------------------");


        System.out.println("Response Code : " + responseCode);
        System.out.println("Request - Headers: ");
        for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("\n--------------------------");

        System.out.println("Response - Headers: ");
        for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("\n--------------------------");
        //print result
        System.out.println(response.toString());


    }


    // HTTP POST request
    private void sendPost() throws Exception {

        String url = "http://jsonplaceholder.typicode.com/posts";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "title=foo&body=bar&userId=1";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("No se encontro nada");
            System.out.println(e);
        }
    }

    //TODO Corregir
    // HTTP PUT request
    private void sendPut() throws Exception {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("PUT");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "id=1&title=PUT&body=metodoPUT&userId=1";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'PUT' request to URL : " + url);
        System.out.println("PUT parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("No se encontro nada");
            System.out.println(e);
        }
    }

    //HTTP DELETE request
    private void sendDelete() throws Exception {
        String url = "http://jsonplaceholder.typicode.com/posts/1";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("No se encontro nada");
            System.out.println(e);
        }
    }
}