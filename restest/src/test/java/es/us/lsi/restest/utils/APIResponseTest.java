package es.us.lsi.restest.utils;


import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class APIResponseTest {
    private static Logger logger = Logger.getRootLogger();
    private APIResponse sone = null, stwo = null;

    public void setUp() {
        logger.info("getting singleton...");
        sone = APIResponse.getInstance();
        logger.info("...got singleton: " + sone);
        logger.info("getting singleton...");
        stwo = APIResponse.getInstance();
        logger.info("...got singleton: " + stwo);
    }

    @Test
    public void testUnique() {
        logger.info("checking singletons for equality");
        Assert.assertEquals(true, sone == stwo);
    }
}
