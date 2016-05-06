package es.us.lsi.restest.engine;

import org.junit.Assert;
import org.junit.Test;

import static es.us.lsi.restest.controllers.RequestController.responseValues;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class RequestAnswerTest {
    private String urlSpotify = "https://api.spotify.com/v1/search?q=Justin+Timberlake&type=artist";
    private String urlJsonPlaceHolder = "http://jsonplaceholder.typicode.com/posts";
    private String genericUrl = "http://httpbin.org/delete";

    @org.junit.Test
    public void testSendGet() throws Exception {
        String headers = new String("{\r\n \r\n}");
        Integer connectionTimeout = new Integer("0");
        Integer socketTimeout = new Integer("0");
        String testToPerform = "";

        RequestAnswer.sendGet(urlSpotify, headers, connectionTimeout, socketTimeout, testToPerform);

        assertNotNull(responseValues.getResponseCode());

        assertNotNull(responseValues.getResponse());

        assertTrue(responseValues.getResponseHeaders().size() > 0);

    }

    @Test
    public void testSendPost() throws Exception {
        String params = new String("{'id': 1,'title':'titleToTest','body': 'bodyToTest','userId': 1 }");
        String headers = new String("{\r\n \r\n}");
        Integer connectionTimeout = new Integer("0");
        Integer socketTimeout = new Integer("0");
        String testToPerform = "";

        RequestAnswer.sendPost(urlJsonPlaceHolder, params, headers, connectionTimeout, socketTimeout, testToPerform);

        Assert.assertEquals("201 Created", responseValues.getResponseCode());
        assertTrue(responseValues.getResponse().toString().length() > 0);
    }

    @Test
    public void testSendPut() throws Exception {
        String params = new String("{'id': 1,'title':'titleToTest','body': 'bodyToTest','userId': 1 }");
        String headers = new String("{\r\n \r\n}");
        Integer connectionTimeout = new Integer("0");
        Integer socketTimeout = new Integer("0");
        String testToPerform = "";

        RequestAnswer.sendGet(urlJsonPlaceHolder + "/1", headers, connectionTimeout, socketTimeout, testToPerform);
        String responseGet = new String(responseValues.getResponse());

        RequestAnswer.sendPut(urlJsonPlaceHolder + "/1", params, headers, connectionTimeout, socketTimeout, testToPerform);
        String responsePut = new String(responseValues.getResponse());

        Assert.assertNotEquals(responseGet, responsePut);
    }

    @Test
    public void testSendDelete() throws Exception {
        String params = new String("{\n" + " \n" + "}");
        String headers = new String("{\r\n \r\n}");
        Integer connectionTimeout = new Integer("0");
        Integer socketTimeout = new Integer("0");
        String testToPerform = "";

        RequestAnswer.sendDelete(genericUrl, params, headers, connectionTimeout, socketTimeout, testToPerform);

        Assert.assertEquals("200 OK", responseValues.getResponseCode());

        params = new String("{\r\n 'name': 'mark' \r\n}");
        RequestAnswer.sendDelete(genericUrl, params, headers, connectionTimeout, socketTimeout, testToPerform);

        assertTrue(responseValues.getResponse().toString().contains("mark"));
    }
}