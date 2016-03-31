package es.us.lsi.restest.engine;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
    public static HashMap<String, Map<String, Boolean>> resultMapQuery = new HashMap<>();
    public static HashMap<String, Map<String, Boolean>> resultMapPath = new HashMap<>();
    private static Map<String, Boolean> testValor = new HashMap<>();


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
        boolean res = false;
        if (!param.isEmpty()) {
            if (Character.isLowerCase(param.charAt(0)) && !StringUtils.isAllUpperCase(param)) {
                for (int i = 1; i <= param.length() - 1; i++) {
                    if (Character.isUpperCase(param.charAt(i)))
                        res = true;
                }
            }
        }
        return res;
    }

    /**
     * Comprueba que la uri contiene palabras con la forma PascalCase
     *
     * @param param
     * @return true si la @uri tiene palabras PascalCase
     */
    private static boolean testPascalCase(String param) {
        boolean res = false;
        if (!param.isEmpty()) {
            if (Character.isUpperCase(param.charAt(0)) && !StringUtils.isAllUpperCase(param)) {
                for (int i = 1; i <= param.length() - 1; i++) {
                    if (Character.isUpperCase(param.charAt(i)))
                        res = true;
                }
            }
        }
        return res;
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
        return param.matches("(\\d+.\\d+?)|(\\d+)");
    }

    private static boolean testAlphanumeric(String param) {
        return StringUtils.isAlphanumeric(param);
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
        System.out.println(value + " ->" + " test PascalCase: " + testPascalCase(value));
        System.out.println(value + " ->" + " test Snake_case: " + testSnakeCase(value));
        System.out.println(value + " ->" + " test Alphanumeric: " + testAlphanumeric(value));
    }

    private static Map<String, Boolean> setTestToResult(String string) {
        Map<String, Boolean> valores = new HashMap<>();
        valores.put("fullNumber", false);
        valores.put("hyphen", false);
        valores.put("camelCase", false);
        valores.put("PascalCase", false);
        valores.put("Snake_case", false);
        valores.put("Alphanumeric", false);

        valores.put("fullNumber", testFullNumber(string));
        valores.put("hyphen", testHyphen(string));
        valores.put("camelCase", testCamelCase(string));
        valores.put("PascalCase", testPascalCase(string));
        valores.put("Snake_case", testSnakeCase(string));
        valores.put("Alphanumeric", testAlphanumeric(string));

        return valores;
    }

    private static void resetMap(Map<String, Boolean> valores) {
        valores.put("fullNumber", false);
        valores.put("hyphen", false);
        valores.put("camelCase", false);
        valores.put("PascalCase", false);
        valores.put("Snake_case", false);
        valores.put("Alphanumeric", false);
    }

    public static void checkURL(URL url) throws UnsupportedEncodingException {
        resultMapQuery = new HashMap<>();
        resultMapPath = new HashMap<>();
        testValor = new HashMap<>();

        String path = url.getPath();
        String fileName = url.getFile();
        String query = url.getQuery();

        String[] pathParts = path.split("/");
        for (int i = 1; i <= pathParts.length - 1; i++) {
            showResults(pathParts[i]);

            resultMapPath.put(pathParts[i], new HashMap<>(setTestToResult(pathParts[i])));
        }

        String[] fileNameParts = fileName.split("/");
        for (int i = 1; i <= fileNameParts.length - 1; i++) {
            showResults(fileNameParts[i]);
        }
        if (query != null) {
            String[] queryParts = query.split("&");
            for (String pair : queryParts) {
                int idx = pair.indexOf("=");
                String part1 = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String part2 = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");

                showResults(part1);
                showResults(part2);

                resultMapQuery.put(part1, new HashMap<>(setTestToResult(part1)));

                resultMapQuery.put(part2, new HashMap<>(setTestToResult(part2)));
            }
        }
    }
}
