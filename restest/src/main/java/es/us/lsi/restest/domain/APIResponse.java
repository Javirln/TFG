package es.us.lsi.restest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by javirln on 08/02/16.
 */
public class APIResponse {

    private Map<String, List<String>> requestHeaders = new HashMap<>();
    private Map<String, List<String>> responseHeaders = new HashMap<>();
    private Map<String, String> generalInfo = new HashMap<>();
    private StringBuffer response = new StringBuffer();

    public APIResponse() {
        super();
    }

    public APIResponse(Map<String, List<String>> requestHeaders, Map<String, List<String>> responseHeaders, Map<String, String> generalInfo, StringBuffer response) {
        this.requestHeaders = requestHeaders;
        this.responseHeaders = responseHeaders;
        this.generalInfo = generalInfo;
        this.response = response;
    }

    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, List<String>> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public Map<String, String> getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(Map<String, String> generalInfo) {
        this.generalInfo = generalInfo;
    }

    public StringBuffer getResponse() {
        return response;
    }

    public void setResponse(StringBuffer response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "response=" + response +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        APIResponse that = (APIResponse) o;

        if (!requestHeaders.equals(that.requestHeaders)) return false;
        if (!responseHeaders.equals(that.responseHeaders)) return false;
        return response.equals(that.response);

    }

    @Override
    public int hashCode() {
        int result = requestHeaders.hashCode();
        result = 31 * result + responseHeaders.hashCode();
        result = 31 * result + response.hashCode();
        return result;
    }
}
