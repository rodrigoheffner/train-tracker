/*
 * Copyright 2016 rodrigo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rodrigobezerra;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.rodrigobezerra.model.TrainTime;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author rodrigo
 */
public class Service {

    public static void main(String[] args) throws IOException {
        Service.getNextDeparture("LWM", "CDF");
    }

    public static TrainTime getNextDeparture(String from, String to) throws MalformedURLException,
            IOException {

//Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
//        String wsURL = "http://www.deeptraining.com/webservices/weather.asmx";
//        String wsURL = "https://lite.realtime.nationalrail.co.uk/OpenLDBWS/wsdl.aspx?ver=2015-11-27";
        String wsURL = "https://lite.realtime.nationalrail.co.uk/OpenLDBWS/ldb8.asmx";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput = getXmlInput(from, to);

        byte[] buffer = new byte[xmlInput.length()];
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
//        String SOAPAction = "http://litwinconsulting.com/webservices/GetWeather";
//        String SOAPAction = "GetNextDepartures";
        String SOAPAction = "http://thalesgroup.com/RTTI/2015-05-14/ldb/GetNextDepartures";
// Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
                String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
//Write the content of the request to the outputstream of the HTTP Connection.
        out.write(b);
        out.close();
//Ready with sending the request.

//Read the response.
        InputStreamReader isr
                = new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

//Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }

        TrainTime trainTime = null;
        
        try {
//Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        Document document = parseXmlFile(outputString);
//        NodeList nodeLst = document.getElementsByTagName("GetWeatherResult");
        NodeList standardDepartureTime = document.getElementsByTagName("lt4:std");
        NodeList estimatedDepartureTime = document.getElementsByTagName("lt4:etd");
//        String weatherResult = nodeLst.item(0).getTextContent();

//Write the SOAP message formatted to the console.
        String formattedSOAPResponse = formatXML(outputString);
        System.out.println(formattedSOAPResponse);

        trainTime = new TrainTime(standardDepartureTime.item(0).getTextContent(),
                estimatedDepartureTime.item(0).getTextContent(), outputString);
        } catch (Exception e) {
            trainTime = new TrainTime("error", "error", outputString);
        }
        return trainTime;
    }

    //format the XML in your String
    public static String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);
            format.setIndent(3);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getXmlInput(String from, String to) {
//        return " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n"
//                + " <soapenv:Header/>\n"
//                + " <soapenv:Body>\n"
//                + " <web:GetWeather>\n"
//                + " <!--Optional:-->\n"
//                + " <web:City>" + city + "</web:City>\n"
//                + " </web:GetWeather>\n"
//                + " </soapenv:Body>\n"
//                + " </soapenv:Envelope>";

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://thalesgroup.com/RTTI/2013-11-28/Token/types\" xmlns:ldb=\"http://thalesgroup.com/RTTI/2015-11-27/ldb/\">"
                + "   <soapenv:Header>"
                + "      <typ:AccessToken>"
                + "         <typ:TokenValue>ffc81438-b053-4e01-b7b9-dc53a0c73709</typ:TokenValue>"
                + "      </typ:AccessToken>"
                + "   </soapenv:Header>"
                + "   <soapenv:Body>"
                + "      <ldb:GetNextDeparturesRequest>"
                + "         <ldb:crs>" + from + "</ldb:crs>"
                + "         <ldb:filterList>"
                + "            <!--1 or more repetitions:-->"
                + "            <ldb:crs>" + to + "</ldb:crs>"
                + "         </ldb:filterList>"
                + "         <!--Optional:-->"
                + "         <ldb:timeOffset>0</ldb:timeOffset>"
                + "         <!--Optional:-->"
                + "         <ldb:timeWindow>120</ldb:timeWindow>"
                + "      </ldb:GetNextDeparturesRequest>"
                + "   </soapenv:Body>"
                + "</soapenv:Envelope>";
    }
}
