package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppServerTest extends TestCase {

    public AppServerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppServerTest.class);
    }


    public void testApp() {
        assertTrue(true);
    }

}
