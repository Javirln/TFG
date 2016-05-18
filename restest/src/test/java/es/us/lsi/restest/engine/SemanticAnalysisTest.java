package es.us.lsi.restest.engine;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

public class SemanticAnalysisTest {
    private String url = "http://example.com/api/v2/parent-node/child_node/grandChildNode/GrandGranChildNode/user12/3491/resource/data.json";

    @Test
    public void testVersion() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean version = SemanticAnalysis.resultMapPath.get("v2").get("Version");

        Assert.assertEquals(true, version);
    }

    @Test
    public void testHyphen() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean hyphen = SemanticAnalysis.resultMapPath.get("parent-node").get("hyphen");

        Assert.assertEquals(true, hyphen);
    }

    @Test
    public void testSnakeCase() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean snakeCase = SemanticAnalysis.resultMapPath.get("child_node").get("Snake_case");

        Assert.assertEquals(true, snakeCase);
    }

    @Test
    public void testCamelCase() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean camelCase = SemanticAnalysis.resultMapPath.get("grandChildNode").get("camelCase");

        Assert.assertEquals(true, camelCase);
    }

    @Test
    public void testPascalCase() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean pascalCase = SemanticAnalysis.resultMapPath.get("GrandGranChildNode").get("PascalCase");

        Assert.assertEquals(true, pascalCase);
    }

    @Test
    public void testAlphanumeric() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean alphanumeric = SemanticAnalysis.resultMapPath.get("user12").get("Alphanumeric");

        Assert.assertEquals(true, alphanumeric);
    }

    @Test
    public void testFullNumber() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean fullNumber = SemanticAnalysis.resultMapPath.get("3491").get("fullNumber");

        Assert.assertEquals(true, fullNumber);
    }

    @Test
    public void testLowercase() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean lowercase = SemanticAnalysis.resultMapPath.get("resource").get("Lowercase");

        Assert.assertEquals(true, lowercase);
    }

    @Test
    public void testExtension() throws Exception {
        SemanticAnalysis.checkURL(new URL(url));

        Boolean lowercase = SemanticAnalysis.resultMapPath.get(url).get("Extension");

        Assert.assertEquals(true, lowercase);
    }

    @Test
    public void testTrailing() throws Exception {
        String urlTrailing = "http://example.com/api/v2/parent-node/child_node/grandChildNode/GrandGranChildNode/user12/3491/resource/data.json/";
        SemanticAnalysis.checkURL(new URL(urlTrailing));

        Boolean trailingSlash = SemanticAnalysis.resultMapPath.get(urlTrailing).get("Trailing forward slash");

        Assert.assertEquals(true, trailingSlash);
    }
}