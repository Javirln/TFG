package es.us.lsi.restest.engine;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

public class RequestAnswerTest {
    private String url = "http://jsonplaceholder.typicode.com/posts";
    private String key = "id";

    @org.junit.Test
    public void statusCode() throws Exception {
        when().get(url).then().statusCode(200);
    }

    @org.junit.Test
    public void responseTime() {
        when().get(url).then().time(lessThan(2000L));
    }

    @org.junit.Test
    public void successfulPostRequest() {
        given().param("title", "foo").param("id", "1").header("Content-Type", "application/x-www-form-urlencoded").when().post(url).then().statusCode(415);
    }

    @org.junit.Test
    public void keyValue() {
        when().get(url).then().body(this.key, is(1));
    }

}