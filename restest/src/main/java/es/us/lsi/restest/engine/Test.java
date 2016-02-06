package es.us.lsi.restest.engine;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
    /**
     * Comprueba que la uri que recibe es de la de forma Hyphen
     *
     * @param param
     * @return true si es la @uri es de la forma Hyphen
     */
    private static boolean testHyphen(String param) {
        return param.matches("(.*)-(.*)");
    }

    /**
     * Comprueba que la uri que recibe es de la de forma Snake
     *
     * @param param
     * @return true si es la @uri es de la forma Snake
     */
    private static boolean testSnakeCase(String param) {
        return param.matches("(.*)_(.*)");
    }

    /**
     * Comprueba que la uri contiene palabras con la forma camelCase
     *
     * @param param
     * @return true si la @uri tiene palabras camelCase
     */
    private static boolean testCamelCase(String param) {
        return param.matches("[a-zA-Z]+");
    }

    /**
     * Devuelve el top-level domain de una uri dada.
     * Hay que hacer un lowerCase a la @uri primero.
     *
     * @param param
     */
    private static void getTopDomainLevel(String param) {
        Pattern pattern = Pattern.compile("(\\.[a-z]{3}|\\.[a-z]{2})$");
        Matcher matcher = pattern.matcher(param);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    /**
     * Comprueba que @uri es un número
     *
     * @param param
     * @return true si @uri es un número
     */
    private static boolean testFullNumber(String param) {
        return param.matches("(\\d+(.\\d+)?)");
    }

    private static boolean testAlphanumeric(String param) {
        return param.matches("^\\w+$");
    }

    /**
     * Comprueba si la URL acaba en /
     *
     * @param param
     * @return true si la URL acaba en /
     */
    private static boolean testTrailingForwardSlash(String param) {
        boolean res = false;
        try {
            res = param.charAt(param.length() - 1) == '/';
        } catch (StringIndexOutOfBoundsException siobe) {
            System.out.println(siobe.toString());
        }
        return res;
    }

    private static void showResults(String value) {
        System.out.println(value + " ->" + " test fullNumber: " + testFullNumber(value));
        System.out.println(value + " ->" + " test hyphen: " + testHyphen(value));
        System.out.println(value + " ->" + " test camelCase: " + testCamelCase(value));
        System.out.println(value + " ->" + " test Snake_case: " + testSnakeCase(value));
        System.out.println(value + " ->" + " test Alphanumeric: " + testAlphanumeric(value));
    }

    public static void syntaxTest(URL url) {
        String urlToString = url.toString();
        //Parseamos la url usando /
        String[] parts = urlToString.split("/");
        for (int i = 0; i <= parts.length - 1; i++) {
            //Comprobamos que la parte en la que estamos no pertenece a la parte de la query
            if (parts[i].contains("&")) {
                Map<String, String> queryDic = new HashMap<>();
                String queryParams = parts[i];
                String[] queryParts = queryParams.split("&");
                System.out.println("---Query part---");
                for (int j = 0; j <= queryParts.length - 1; j++) {
                    String[] paramsToMap = queryParts[j].split("=");
                    //Desechamos la parte izquierda del ?
                    if (paramsToMap[0].contains("?")) {
                        String[] aux = paramsToMap[0].split("\\?");
                        queryDic.put(aux[1], paramsToMap[1]);
                        continue;
                    }
                    queryDic.put(paramsToMap[0], paramsToMap[1]);
                }
                for (Map.Entry<String, String> entry : queryDic.entrySet()) {
                    System.out.println(entry.getKey() + " ==> " + entry.getValue());
                    showResults(entry.getKey());
                }
                continue;
            }
            showResults(parts[i]);
        }
        System.out.println(urlToString + " ->" + " test TrailingSlash: " + testTrailingForwardSlash(urlToString));
    }
}
