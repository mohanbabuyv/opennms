package org.opennms.features.reporting.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DefaultLocalReportsDaoTest {
    
    @Before
    public void setup() {
        System.setProperty("opennms.home", "src/test/resources");
     }
    
    @Test
    public void setupTest() throws IOException {
        System.setProperty("opennms.home", "src/test/resources");
        assertEquals("src/test/resources", System.getProperty("opennms.home"));
        
        String pathToConfigXml = System.getProperty("opennms.home") + 
                File.separator + 
                "etc" + 
                File.separator + 
                "local-reports.xml";
        
        System.out.println(pathToConfigXml);
        
        
        File testFile = new File(pathToConfigXml);
        assertTrue(testFile.exists());
        assertNotNull(testFile);
        assertTrue(testFile.canRead());
        assertTrue(testFile.canWrite());
        assertFalse(testFile.canExecute());
    }
    
    @Test
    public void getReportsCountTest () {
        LegacyLocalReportsDao m_dao = new LegacyLocalReportsDao();
        assertNotNull(m_dao);
        assertNotNull(m_dao.getReports());
        assertEquals(18, m_dao.getReports().size());
        
//        for (BasicReportDefinition report : m_dao.getReports()) {
//            System.out.println(report.getDisplayName());
//        }
    }
}
