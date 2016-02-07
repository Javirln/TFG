package es.us.lsi.restest.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLForm {
    private String URL = "";
    private String METHOD = "";
    private String RESPONSE_CODE = "";
    private Map<String, List<String>> HEADERS = new HashMap<>();

    public URLForm() {

    }

    public URLForm(String URL) {
        this.URL = URL;
    }

    public URLForm(String URL, String METHOD, String RESPONSE_CODE, Map<String, List<String>> HEADERS) {
        this.URL = URL;
        this.METHOD = METHOD;
        this.RESPONSE_CODE = RESPONSE_CODE;
        this.HEADERS = HEADERS;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMETHOD() {
        return METHOD;
    }

    public void setMETHOD(String METHOD) {
        this.METHOD = METHOD;
    }

    public String getRESPONSE_CODE() {
        return RESPONSE_CODE;
    }

    public void setRESPONSE_CODE(String RESPONSE_CODE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
    }

    public Map<String, List<String>> getHEADERS() {
        return HEADERS;
    }

    public void setHEADERS(Map<String, List<String>> HEADERS) {
        this.HEADERS = HEADERS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        URLForm urlFrom = (URLForm) o;

        if (!URL.equals(urlFrom.URL)) return false;
        if (!METHOD.equals(urlFrom.METHOD)) return false;
        if (!RESPONSE_CODE.equals(urlFrom.RESPONSE_CODE)) return false;
        return HEADERS.equals(urlFrom.HEADERS);

    }

    @Override
    public int hashCode() {
        int result = URL.hashCode();
        result = 31 * result + METHOD.hashCode();
        result = 31 * result + RESPONSE_CODE.hashCode();
        result = 31 * result + HEADERS.hashCode();
        return result;
    }
}
