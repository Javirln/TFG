package es.us.lsi.restest.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIResponse {

    private Map<String, List<String>> responseHeaders = new HashMap<>();
    private String responseCode = "";
    private Map<String, String> requestHeaders = new HashMap<>();
    private Map<String, String> generalInfo = new HashMap<>();
    private StringBuilder response = new StringBuilder();
    private Long responseTime = 0L;

    public APIResponse() {
        super();
    }

    public int getContentType() throws Exception {
        int res = -1;
        try {
            if (!getResponseHeaders().isEmpty()) {
                res = getResponseHeaders().get("content-type").get(0).indexOf(';');
            }
        } catch (NullPointerException e) {
            return res;
        }
        return res;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
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

    public StringBuilder getResponse() {
        return response;
    }

    public void setResponse(StringBuilder response) {
        this.response = response;
    }

    public void resetProperties() {
        this.responseHeaders = new HashMap<>();
        this.responseCode = "";
        this.requestHeaders = new HashMap<>();
        this.generalInfo = new HashMap<>();
        this.response = new StringBuilder();
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

        if (!requestHeaders.equals(requestHeaders)) return false;
        if (!responseHeaders.equals(responseHeaders)) return false;
        return response.equals(response);

    }

    @Override
    public int hashCode() {
        int result = requestHeaders.hashCode();
        result = 31 * result + responseHeaders.hashCode();
        result = 31 * result + response.hashCode();
        return result;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }
}
