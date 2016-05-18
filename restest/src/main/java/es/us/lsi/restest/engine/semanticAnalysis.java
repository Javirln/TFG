package es.us.lsi.restest.engine;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static es.us.lsi.restest.controllers.RequestController.exceptionMessages;

@Service
public class SemanticAnalysis {
    public static HashMap<String, Map<String, Boolean>> resultMapQuery = new HashMap<>();
    public static HashMap<String, Map<String, Boolean>> resultMapPath = new HashMap<>();


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
     * Devuelve el top-level utils de una uri dada.
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
     * Comprueba si la URL contiene un parametro con la versión de la API
     *
     * @param param
     * @return true si contiene el parametro de la version
     */
    private static boolean testVersioning(String param) {
        return param.startsWith("v") && param.substring(1, param.length()).matches("(\\d+.\\d+?)|(\\d+)");
    }

    /**
     * Comprueba si la URL está en minúscula
     *
     * @param param
     * @return true si está en minúscula
     */
    private static boolean testLowerCase(String param) {
        return StringUtils.isAllLowerCase(param);
    }

    /**
     * Comprueba si la URL termina en /
     *
     * @param url
     * @return true si termina en /
     */
    private static boolean testTrailingForwardSlash(String url) {
        return url.charAt(url.length() - 1) == '/';
    }

    /**
     * Comprueba si la URL termina en una extension
     *
     * @param url
     * @return true si contiene una extension registrada
     */
    private static boolean testExtension(String url) {
        boolean res = false;
        try {
            StringBuilder fileToString = new StringBuilder();

            File file = ResourceUtils.getFile("classpath:mime.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                fileToString.append(line).append("\n");
            }
            scanner.close();

            JSONObject json = new JSONObject(fileToString.toString());
            Iterator<String> iter = json.keys();

            while (iter.hasNext()) {
                String key = iter.next();
                if (url.endsWith("." + key)) {
                    res = true;
                    break;
                }
            }
        } catch (IOException e) {
            exceptionMessages.put("parser", "There has been a problem parsing an internal file.");
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
        System.out.println(value + " ->" + " test Version: " + testVersioning(value));
        System.out.println(value + " ->" + " test Lowercase: " + testLowerCase(value));
    }

    private static Map<String, Boolean> setTestToResult(String string) {
        Map<String, Boolean> values = new HashMap<>();
        values.put("fullNumber", false);
        values.put("hyphen", false);
        values.put("camelCase", false);
        values.put("PascalCase", false);
        values.put("Snake_case", false);
        values.put("Alphanumeric", false);
        values.put("Version", false);
        values.put("Lowercase", false);


        values.put("fullNumber", testFullNumber(string));
        values.put("hyphen", testHyphen(string));
        values.put("camelCase", testCamelCase(string));
        values.put("PascalCase", testPascalCase(string));
        values.put("Snake_case", testSnakeCase(string));
        values.put("Alphanumeric", testAlphanumeric(string));
        values.put("Version", testVersioning(string));
        values.put("Lowercase", testLowerCase(string));

        return values;
    }

    public static void checkURL(URL url) throws UnsupportedEncodingException {
        resultMapQuery = new HashMap<>();
        resultMapPath = new HashMap<>();

        Map<String, Boolean> generalTest = new HashMap<>();

        String path = url.getPath();
        String fileName = url.getFile();
        String query = url.getQuery();

        generalTest.put("Trailing forward slash", testTrailingForwardSlash(url.toString()));
        generalTest.put("Extension", testExtension(url.toString()));

        resultMapPath.put(url.toString(), new HashMap<>(generalTest));

        String[] pathParts = path.split("/");
        for (int i = 1; i <= pathParts.length - 1; i++) {
            //showResults(pathParts[i]);

            resultMapPath.put(pathParts[i], new HashMap<>(setTestToResult(pathParts[i])));
        }

        String[] fileNameParts = fileName.split("/");
        for (int i = 1; i <= fileNameParts.length - 1; i++) {
            //showResults(fileNameParts[i]);
        }
        if (query != null) {
            String[] queryParts = query.split("&");
            for (String pair : queryParts) {
                int idx = pair.indexOf("=");
                String part1 = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String part2 = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");

                //showResults(part1);
                //showResults(part2);

                resultMapQuery.put(part1, new HashMap<>(setTestToResult(part1)));

                resultMapQuery.put(part2, new HashMap<>(setTestToResult(part2)));
            }
        }
    }
}