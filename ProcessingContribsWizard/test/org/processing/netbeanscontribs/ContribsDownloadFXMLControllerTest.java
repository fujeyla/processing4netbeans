/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.processing.netbeanscontribs;

import org.processing.netbeanscontribs.ContribsDownloadController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ContribsDownloadFXMLControllerTest {
    
    public ContribsDownloadFXMLControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of extractAuthorName method, of class ContribsDownloadFXMLController.
     */
    @Test
    public void testExtractAuthorName() {
        System.out.println("extractAuthorName");
        String authorsText = "[Daniel Shiffman](http://shiffman.net/) and [Thomas Sanchez](http://codigogenerativo.com/)";
        ContribsDownloadController instance = new ContribsDownloadController();
        String expResult = "Daniel Shiffman,Thomas Sanchez";
        String result = instance.extractAuthorName(authorsText);
        assertEquals(expResult, result);
        authorsText = "My name is...";
        result = instance.extractAuthorName(authorsText);
        assertEquals(authorsText, result);
    }


    
}
