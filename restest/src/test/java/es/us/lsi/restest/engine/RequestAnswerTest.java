package es.us.lsi.restest.engine;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.lessThan;

public class RequestAnswerTest {
    private String url = "http://jsonplaceholder.typicode.com/posts";

    @org.junit.Test
    public void statusCode() throws Exception {
        when().get(url).then().statusCode(200);
    }

    @org.junit.Test
    public void responseTime() {
        when().get(url).then().time(lessThan(2000L));
    }
}