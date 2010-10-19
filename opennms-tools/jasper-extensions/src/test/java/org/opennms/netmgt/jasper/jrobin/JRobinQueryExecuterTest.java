package org.opennms.netmgt.jasper.jrobin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;

import org.junit.Before;
import org.junit.Test;

public class JRobinQueryExecuterTest {
    
    private JasperReport m_jasperReport;
    private JasperPrint m_jasperPrint;
    
    @Before
    public void setUp() {
        File reportDir = new File("target/reports");
        reportDir.mkdirs();
    }
    
    /**
     *
     */
    @Test
    public void test() throws JRException{
        compile();
        fill();
        pdf();
        xhtml();
    }

    public void compile() throws JRException {
        // jrxml compiling process
        m_jasperReport = JasperCompileManager.compileReport("src/test/resources/RrdGraph.jrxml");
        
    }

    /**
     *
     */
    public void fill() throws JRException
    {
        long start = System.currentTimeMillis();
        Map params = new HashMap();
        params.put("rrdDir", "src/test/resources");
        m_jasperPrint = JasperFillManager.fillReport(m_jasperReport, params);
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }

    /**
     *
     */
    public void pdf() throws JRException
    {
        long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile(m_jasperPrint, "target/reports/RrdGraph.pdf");
        System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
    }
    
    /**
     *
     */
    public void xhtml() throws JRException
    {
        long start = System.currentTimeMillis();

        File destFile = new File("target/reports/RrdGraph.x.html");
        
        JRXhtmlExporter exporter = new JRXhtmlExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, m_jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        
        exporter.exportReport();

        System.err.println("XHTML creation time : " + (System.currentTimeMillis() - start));
    }


}